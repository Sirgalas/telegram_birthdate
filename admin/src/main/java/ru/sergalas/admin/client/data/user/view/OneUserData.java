package ru.sergalas.admin.client.data.user.view;

import java.util.List;

public record OneUserData(
      String id,
      String username,
      String email,
      String firstName,
      String lastName,
      List<String> roles) {
}
