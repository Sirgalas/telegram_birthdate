package ru.sergalas.security.service;

import ru.sergalas.security.data.UserCreateRecord;
import ru.sergalas.security.data.UserResponseListRecord;
import ru.sergalas.security.data.UserResponseRecord;
import ru.sergalas.security.data.UserUpdateRecord;

import java.util.List;

public interface UserService {
    public UserResponseRecord createUser(UserCreateRecord createRecord);
    public UserResponseRecord updateUser(String userId, UserUpdateRecord userUpdateRecord);
    public UserResponseListRecord getAllUser(String userName, Integer first, Integer count);
    public UserResponseRecord getUser(String userId);
}
