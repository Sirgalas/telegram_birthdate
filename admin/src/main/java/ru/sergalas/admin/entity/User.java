package ru.sergalas.admin.entity;

public record User(
        String username,
        String email,
        String firstName,
        String lastName,
        String password,
        String role
) {
}
