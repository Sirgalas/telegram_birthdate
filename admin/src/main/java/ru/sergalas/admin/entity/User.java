package ru.sergalas.admin.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record User(
        String id,
        @NotBlank(message = "Username is required")
        String username,
        @Email(message = "Invalid email format")
        String email,
        String firstName,
        String lastName,
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at  least 6 charaster")
        String password,
        List<String> role
) {
}
