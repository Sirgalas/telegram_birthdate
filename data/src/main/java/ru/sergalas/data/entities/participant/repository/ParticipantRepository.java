package ru.sergalas.data.entities.participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sergalas.data.entities.participant.entity.Participant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    @Query(value = """
        SELECT *
        FROM participant
        INNER JOIN date_periodicity on date_periodicity.id = participant.date_periodicity_id
        WHERE  date_periodicity.date = :date  
    """, nativeQuery = true)
    List<Participant> getParticipantByDate(String date);
}