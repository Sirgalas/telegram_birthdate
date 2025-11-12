package ru.sergalas.admin.client.data.register.create;

public record CreateRequestData(
        String username,
        String email,
        String firstName,
        String lastName,
        String password,
        String role
) {
}
