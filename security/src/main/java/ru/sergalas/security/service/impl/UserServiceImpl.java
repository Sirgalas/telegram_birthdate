package ru.sergalas.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.sergalas.security.data.UserCreateRecord;
import ru.sergalas.security.data.UserUpdateRecord;
import ru.sergalas.security.mapper.UserMapper;
import jakarta.ws.rs.core.Response;
import org.springframework.http.HttpStatus;
import jakarta.ws.rs.NotFoundException;
import ru.sergalas.security.service.UserService;

import java.net.URI;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;
    private final UserMapper userMapper;

    @Value("${app.keycloak.admin.target-realm}")
    private String targetRealm;

    public String createUser(UserCreateRecord createRecord) {
        UserRepresentation user = userMapper.toUserRepresentation(createRecord);
        user.setEnabled(true);
        user.setEmailVerified(true);
        CredentialRepresentation credential = createCredentialRepresentation(createRecord.password());
        user.setCredentials(Collections.singletonList(credential));
        try {
            Response response = keycloak.realm(targetRealm).users().create(user);
            if (response.getStatus() == 201) {
                String createdUserId = getCreatedUserId(response);
                addRoleToUser(createdUserId, createRecord.role());
                return "User created successfully";
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

    public String updateUser(String userId, UserUpdateRecord userUpdateRecord) {
        UserRepresentation existingUser;
        try{
            existingUser = keycloak.realm(targetRealm).users().get(userId).toRepresentation();
        }catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        userMapper.updateUserRepresentation(userUpdateRecord, existingUser);
        if(userUpdateRecord.password() != null){
            CredentialRepresentation credential = createCredentialRepresentation(userUpdateRecord.password());
            keycloak.realm(targetRealm).users().get(userId).resetPassword(credential);
        }
        keycloak.realm(targetRealm).users().get(userId).update(existingUser);
        return "User updated successfully";
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

    // Метод для добавления роли (если role != null)
    private void addRoleToUser(String userId, String role) {
        if (role != null && !role.isEmpty()) {
            RoleRepresentation adminRole = keycloak.realm(targetRealm).roles().get(role).toRepresentation();
            keycloak.realm(targetRealm).users().get(userId).roles().realmLevel().add(Collections.singletonList(adminRole));
        }
    }
}
