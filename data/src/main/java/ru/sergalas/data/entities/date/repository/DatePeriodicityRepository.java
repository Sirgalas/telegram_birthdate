package ru.sergalas.data.entities.date.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sergalas.data.entities.date.entity.DatePeriodicity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DatePeriodicityRepository extends JpaRepository<DatePeriodicity, UUID> {
    Optional<DatePeriodicity> findByDate(String date);
}