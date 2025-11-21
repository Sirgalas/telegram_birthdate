package ru.sergalas.admin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergalas.admin.client.UserClient;
import ru.sergalas.admin.client.data.user.view.ListUserData;
import ru.sergalas.admin.client.data.user.view.OneUserData;
import ru.sergalas.admin.entity.User;
import ru.sergalas.admin.mapper.UserMapper;
import ru.sergalas.admin.services.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final UserClient client;

    @Override
    public List<User> getIndex(String userName, Integer first, Integer count) {
        return client.getIndex(userName,first,count).users().stream().map(mapper::fromOneInfo).toList();
    }

    @Override
    public User getOneUser(String id) {
        return mapper.fromOneInfo(client.getOneUser(id));
    }
}
