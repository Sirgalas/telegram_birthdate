package ru.sergalas.admin.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;
import ru.sergalas.admin.client.UserClient;

@RequiredArgsConstructor
public class UserClientImpl implements UserClient {
    private final RestClient client;
}
