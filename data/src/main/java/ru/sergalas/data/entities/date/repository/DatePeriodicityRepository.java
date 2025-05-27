package ru.sergalas.data.entities.date.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sergalas.data.entities.date.entity.DatePeriodicity;

import java.util.UUID;

public interface DatePeriodicityRepository extends JpaRepository<DatePeriodicity, UUID> {
}