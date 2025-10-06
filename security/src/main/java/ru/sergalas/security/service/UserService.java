package ru.sergalas.security.service;

import ru.sergalas.security.data.UserCreateRecord;
import ru.sergalas.security.data.UserUpdateRecord;

public interface UserService {
    public String createUser(UserCreateRecord createRecord);
    public String updateUser(String userId, UserUpdateRecord userUpdateRecord);
}
