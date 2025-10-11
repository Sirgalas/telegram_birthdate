package ru.sergalas.admin.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;
import ru.sergalas.admin.client.DataClient;

@RequiredArgsConstructor
public class DataClientImpl implements DataClient {
    private final RestClient client;
}
