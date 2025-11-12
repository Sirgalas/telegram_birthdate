package ru.sergalas.admin.client.data.register.update;

public record UpdateRequestData(
        String username,
        String email,
        String firstName,
        String lastName,
        String password,
        String role
) {
}
