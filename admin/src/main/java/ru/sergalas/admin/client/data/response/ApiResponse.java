package ru.sergalas.admin.client.data.response;

import ru.sergalas.admin.client.exception.ApiClientException;

public sealed interface ApiResponse<T>  permits ApiResponse.Success, ApiResponse.Failure {
    record Success<T>(T data) implements ApiResponse<T> {}
    record Failure<T>(String error, int code) implements ApiResponse<T> {}
    default boolean isSuccess() {
        return this instanceof Success;
    }

    default boolean isFailure() {
        return this instanceof Failure;
    }

    default T getOrThrow() {
        return switch (this) {
            case Success<T> success -> success.data();     // Если успех - возвращаем данные
            case Failure<T> failure -> throw new ApiClientException(failure.code(), failure.error());
        };
    }
}
