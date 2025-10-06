package ru.sergalas.security.data;

import java.util.List;

public record UserInfoRecord(
        String id,
        String username,
        String email,
        String firstName,
        String lastName,
        List<String> roles
) {
}
