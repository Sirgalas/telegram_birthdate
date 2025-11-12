package ru.sergalas.admin.client.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.sergalas.admin.client.UserClient;
import ru.sergalas.admin.client.data.user.view.ListUserData;
import ru.sergalas.admin.client.data.user.view.OneUserData;

import java.net.URI;
@Service
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

    @Value("${birthday.service.user-service-uri}")
    String baseUri;
    RestClient client;


    @Override
    public ListUserData getIndex(String userName, Integer first, Integer count) {
        URI uri = URI.create(baseUri + "/user");
        UriBuilder uriBuilder = UriComponentsBuilder.fromUri(uri).queryParam("user_name",userName,"first",first,"count",count);
        return client
            .get()
            .uri(uriBuilder.build())
            .retrieve()
            .body(ListUserData.class);
    }

    @Override
    public OneUserData getOneUser(String id) {
        return client
            .get()
            .uri(baseUri + "/user/%s".formatted(id))
            .retrieve()
            .body(OneUserData.class);
    }
}
