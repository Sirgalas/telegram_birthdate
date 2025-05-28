package ru.sergalas.data.entities.user.service;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import ru.sergalas.data.entities.user.data.UserRequestData;
import ru.sergalas.data.entities.user.data.UserResponseData;
import ru.sergalas.data.entities.user.entity.User;

public interface UserService {
    public UserResponseData getUserByUserName(String username);
    public UserResponseData createUser(UserRequestData data) throws BindException;
}
