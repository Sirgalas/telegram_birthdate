package ru.sergalas.admin.client.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.sergalas.admin.client.ClientHandlerRecord;
import ru.sergalas.admin.client.UserClient;
import ru.sergalas.admin.client.data.register.update.UpdateResponseData;
import ru.sergalas.admin.client.data.user.view.ListRolesRecord;
import ru.sergalas.admin.client.data.user.view.ListUserData;
import ru.sergalas.admin.client.data.user.view.OneUserData;
import ru.sergalas.admin.client.helpers.ClientHelper;

import java.net.URI;
@Service
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

    @Value("${birthday.service.user-service-uri}")
    String baseUri;
    private final RestClient client;


    @Override
    public ListUserData getIndex(String userName, Integer first, Integer count) {
        URI uri = URI.create(baseUri + "/user/index");
        UriBuilder uriBuilder = UriComponentsBuilder.fromUri(uri);

        if (userName != null) {
            uriBuilder.queryParam("user_name", userName);
        }
        if (first != null) {
            uriBuilder.queryParam("first", first);
        }
        if (count != null) {
            uriBuilder.queryParam("count", count);
        }
        var send = client
            .get()
            .uri(uriBuilder.build())
            .retrieve();
        return ClientHelper.getReturnedData(
            send,
            new ParameterizedTypeReference<ClientHandlerRecord<ListUserData >>(){}
        );
    }

    @Override
    public OneUserData getOneUser(String id) {
        var send = client.get().uri(URI.create(baseUri + "/user/one/" + id)).retrieve();
        return ClientHelper.getReturnedData(
                send,
                new ParameterizedTypeReference<ClientHandlerRecord<OneUserData >>(){}
        );
    }

    @Override
    public ListRolesRecord getAllRoles() {
        var send = client.get().uri(baseUri + "/user/roles").retrieve();

        return ClientHelper.getReturnedData(
             send,
             new ParameterizedTypeReference<ClientHandlerRecord<ListRolesRecord >>(){}
        );
    }

}
