package ru.sergalas.admin.services;

import ru.sergalas.admin.entity.User;

public interface RegisterService {
    User create(User user);
    User update(User user);
}
