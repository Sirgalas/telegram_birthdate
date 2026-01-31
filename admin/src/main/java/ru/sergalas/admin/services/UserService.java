package ru.sergalas.admin.services;

import ru.sergalas.admin.client.data.user.view.ListUserData;
import ru.sergalas.admin.client.data.user.view.OneUserData;
import ru.sergalas.admin.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getIndex(String userName, Integer first, Integer count);
    User getOneUser(String id);
    List<String> getRoles();
}
