package ru.sergalas.admin.client.data.register.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Map;

public record CreateResponseData(
     @NotBlank(message = "Username is required")
     String username,
     @Email(message = "Invalid email format")
     String email,
     String firstName,
     String lastName,
     @NotBlank(message = "Password is required")
     @Size(min = 6, message = "Password must be at  least 6 charaster")
     String password,
     Map<String, List<String>> attributes,
     List<String> realmRoles
) {
}
