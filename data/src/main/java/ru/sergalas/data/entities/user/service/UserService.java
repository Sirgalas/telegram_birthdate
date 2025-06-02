package ru.sergalas.data.entities.user.service;

import ru.sergalas.data.entities.user.data.UserRequestData;
import ru.sergalas.data.entities.user.data.UserResponseData;

public interface UserService {
    public UserResponseData getUserByUserName(String username);
    public UserResponseData createUser(UserRequestData data);
}
