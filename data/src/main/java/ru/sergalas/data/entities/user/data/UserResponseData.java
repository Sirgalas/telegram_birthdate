package ru.sergalas.data.entities.user.data;

import lombok.Data;

@Data
public class UserResponseData {
    private Long id;
    private String username;
    private String password;
}
