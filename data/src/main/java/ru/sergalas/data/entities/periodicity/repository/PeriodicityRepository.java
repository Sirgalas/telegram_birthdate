package ru.sergalas.data.entities.periodicity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sergalas.data.entities.periodicity.entity.Periodicity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PeriodicityRepository extends JpaRepository<Periodicity, UUID> {
    Optional<Periodicity> findById(UUID id);

    @Query(value = """
        SELECT *
        FROM periodicity
        INNER JOIN date_periodicity on date_periodicity.id = periodicity.date_periodicity_id
        WHERE  date_periodicity.date = :date  
    """, nativeQuery = true)
    List<Periodicity> getPeriodicityByDate(String date);
}