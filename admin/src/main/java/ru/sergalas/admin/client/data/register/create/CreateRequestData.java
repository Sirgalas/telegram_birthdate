package ru.sergalas.admin.client.data.register.create;

import java.util.List;

public record CreateRequestData(
        String username,
        String email,
        String firstName,
        String lastName,
        String password,
        List<String> role
) {
}
