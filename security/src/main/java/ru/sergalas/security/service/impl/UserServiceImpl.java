package ru.sergalas.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.sergalas.security.data.*;
import ru.sergalas.security.mapper.UserMapper;
import jakarta.ws.rs.core.Response;
import org.springframework.http.HttpStatus;
import jakarta.ws.rs.NotFoundException;
import ru.sergalas.security.service.UserService;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;
    private final UserMapper userMapper;

    @Value("${app.keycloak.admin.target-realm}")
    private String targetRealm;

    public UserResponseRecord createUser(UserCreateRecord createRecord) {
        UserRepresentation user = userMapper.toUserRepresentation(createRecord);
        user.setEnabled(true);
        user.setEmailVerified(true);
        CredentialRepresentation credential = createCredentialRepresentation(createRecord.password());
        user.setCredentials(Collections.singletonList(credential));
        try {
            Response response = keycloak.realm(targetRealm).users().create(user);
            if (response.getStatus() == 201) {
                String createdUserId = getCreatedUserId(response);
                /*if(createRecord.role() != null &&  ! createRecord.role().isEmpty()) {
                    for (String role : createRecord.role())  {
                        addRoleToUser(createdUserId, role);
                    }
                }*/
                String userId = CreatedResponseUtil.getCreatedId(response);
                return  userMapper.fromUserRepresentation(keycloak.realm(targetRealm).users().get(userId).toRepresentation());
            } else {

                String errorBody = response.readEntity(String.class);
                throw new ResponseStatusException(HttpStatus.valueOf(response.getStatus()),
                        "Error creating user: " + response.getStatusInfo().getReasonPhrase() + ". Body: " + errorBody);
            }
        } catch (jakarta.ws.rs.ProcessingException e) {
                if (e.getCause() instanceof jakarta.ws.rs.BadRequestException) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Keycloak error: " + e.getMessage(), e);
                }
                throw e;
            }
    }

    public UserResponseRecord updateUser(String userId, UserUpdateRecord userUpdateRecord) {
        UserRepresentation existingUser;
        try{
            existingUser = keycloak.realm(targetRealm).users().get(userId).toRepresentation();
        }catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        userMapper.updateUserRepresentation(userUpdateRecord, existingUser);
        if(!userUpdateRecord.password().isEmpty()){
            CredentialRepresentation credential = createCredentialRepresentation(userUpdateRecord.password());
            keycloak.realm(targetRealm).users().get(userId).resetPassword(credential);
        }
        keycloak.realm(targetRealm).users().get(userId).update(existingUser);
        return userMapper.fromUserRepresentation(keycloak.realm(targetRealm).users().get(userId).toRepresentation());
    }

    public UserResponseListRecord getAllUser(String userName, Integer first, Integer count) {
        RealmResource realmResource = keycloak.realm(targetRealm);
        if(first == null){
            first = 0;
        }
        if(count == null){
            count = -1;
        }
        List<UserRepresentation> users;
        if(userName!=null && !userName.isBlank()){
            users = realmResource.users().search(userName,first,count);
        } else {
            users = realmResource.users()
                    .list(first,count);
        }
        List<UserResponseRecord> result =  users.stream().map(userRepresentation -> {
            UserResource userResource = realmResource.users().get(userRepresentation.getId());
            UserRepresentation fullUser = userResource.toRepresentation();

            List<String> userRoles = userResource.roles().realmLevel().listAll()
                    .stream()
                    .map(RoleRepresentation::getName)
                    .toList();

            return userMapper.fromUserRepresentationWithRole(fullUser,userRoles);
        }).toList();
        return new UserResponseListRecord(result);
    }

    public UserResponseRecord getUser(String userId) {
        return userMapper.fromUserRepresentation(keycloak.realm(targetRealm).users().get(userId).toRepresentation());
    }


    private CredentialRepresentation createCredentialRepresentation(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        return credential;
    }

    private String getCreatedUserId(Response response) {
        URI location = response.getLocation();
        if (location != null) {
            String path = location.getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        }
        throw new IllegalStateException("No user ID in response");
    }

    private void addRoleToUser(String userId, String role) {
        if (role != null && !role.isEmpty()) {
            RoleRepresentation adminRole = keycloak.realm(targetRealm).roles().get(role).toRepresentation();
            keycloak.realm(targetRealm).users().get(userId).roles().realmLevel().add(Collections.singletonList(adminRole));
        }
    }

    public UserRoleResponseRecord getAllRealmRoles() {
        return new UserRoleResponseRecord(keycloak.realm(targetRealm)
                .roles()
                .list()
                .stream()
                .map(RoleRepresentation::getName)
                .toList()
        );
    }

    public void deleteUser(String userId) {
        keycloak.realm(targetRealm).users().get(userId).remove();
    }
}
