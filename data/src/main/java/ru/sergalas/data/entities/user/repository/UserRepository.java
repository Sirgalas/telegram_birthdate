package ru.sergalas.data.entities.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sergalas.data.entities.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}