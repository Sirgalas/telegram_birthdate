package ru.sergalas.security.mapper;

import org.apache.catalina.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.sergalas.security.data.UserCreateRecord;
import ru.sergalas.security.data.UserInfoRecord;
import ru.sergalas.security.data.UserResponseRecord;
import ru.sergalas.security.data.UserUpdateRecord;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "realmRoles", source = "role")
    UserRepresentation toUserRepresentation(UserCreateRecord userCreateRecord);

    UserResponseRecord fromUserRepresentation(UserRepresentation userRepresentation);

    @Mapping(target = "roles", source = "roles")
    UserResponseRecord fromUserRepresentationWithRole(UserRepresentation userRepresentation, List<String> roles);

    @Mapping(target = "realmRoles",source = "role", qualifiedByName = "mapRoleToList")
    void updateUserRepresentation(UserUpdateRecord updateRecord, @MappingTarget UserRepresentation userRepresentation);

    @Mapping(target = "roles", source = "realmRoles")
    UserInfoRecord toUserInfoRecord(UserRepresentation userRepresentation);

    @Named("mapRoleToList")
    default List<String> mapRoleToList(String role) {
        return role != null ? Collections.singletonList(role) : null;
    }
}
