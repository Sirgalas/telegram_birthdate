package ru.sergalas.admin.client.helpers;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import ru.sergalas.admin.client.ClientHandlerRecord;
import ru.sergalas.admin.client.data.participant.view.ParticipantsListRecord;
import ru.sergalas.admin.client.exception.ApiClientException;

public class ClientHelper {
    public static <T> T getReturnedData(RestClient.ResponseSpec client, ParameterizedTypeReference<ClientHandlerRecord<T>> type) {

        ClientHandlerRecord<T> response = client
                .body(type);

        if(response.data()==null){
            throw new ApiClientException(response.code(), response.error());
        }
        return (T) response.data();
    }
    public static <T> T getReturnedData(RestClient.RequestBodySpec client,  ParameterizedTypeReference<ClientHandlerRecord<T>> type) {

        ClientHandlerRecord<T> response = client
                .retrieve()
                .body(type);
        if(response.data()==null){
            throw new ApiClientException(response.code(), response.error());
        }
        return (T) response.data();
    }
}
