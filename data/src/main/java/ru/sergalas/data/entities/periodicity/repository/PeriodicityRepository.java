package ru.sergalas.data.entities.periodicity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sergalas.data.entities.periodicity.entity.Periodicity;

import java.util.UUID;

public interface PeriodicityRepository extends JpaRepository<Periodicity, UUID> {
}