package ru.sergalas.admin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergalas.admin.client.RegisterClient;
import ru.sergalas.admin.client.data.register.create.CreateRequestData;
import ru.sergalas.admin.client.data.register.update.UpdateRequestData;
import ru.sergalas.admin.entity.User;
import ru.sergalas.admin.mapper.UserMapper;
import ru.sergalas.admin.services.RegisterService;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final RegisterClient client;
    private final UserMapper mapper;


    @Override
    public User create(User user) {
        CreateRequestData data = mapper.toCreateRequest(user);
        return mapper.fromCreateResponse(client.create(data));
    }

    @Override
    public User update(User user) {
        UpdateRequestData data = mapper.toUpdateRequest(user);
        return mapper.fromUpdateResponse(client.update(data,user.id()));
    }
}
