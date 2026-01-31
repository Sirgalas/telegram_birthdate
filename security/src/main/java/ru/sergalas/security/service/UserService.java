package ru.sergalas.security.service;

import ru.sergalas.security.data.*;

import java.util.List;

public interface UserService {
    public UserResponseRecord createUser(UserCreateRecord createRecord);
    public UserResponseRecord updateUser(String userId, UserUpdateRecord userUpdateRecord);
    public UserResponseListRecord getAllUser(String userName, Integer first, Integer count);
    public UserResponseRecord getUser(String userId);
    public UserRoleResponseRecord getAllRealmRoles();
    public void deleteUser(String userId);
}
