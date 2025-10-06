package ru.sergalas.security.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRecord(
        @Email(message = "Invalid email format")
        String email,
        String firstName,
        String lastName,
        @Size(min = 6, message = "Password must be at least 6 charaster")
        String password,
        String role
) {
}
