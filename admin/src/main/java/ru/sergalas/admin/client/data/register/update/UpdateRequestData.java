package ru.sergalas.admin.client.data.register.update;

import java.util.List;

public record UpdateRequestData(
        String username,
        String email,
        String firstName,
        String lastName,
        String password,
        List<String> role
) {
}
