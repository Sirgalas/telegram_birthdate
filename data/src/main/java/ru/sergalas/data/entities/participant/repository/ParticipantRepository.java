package ru.sergalas.data.entities.participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sergalas.data.entities.participant.entity.Participant;

import java.util.Optional;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    @Query(value = """
        SELECT *
        FROM participant
        INNER JOIN date_periodicity on date_periodicity.id = participant.
    """, nativeQuery = true)
    Optional<Participant> getParticipantByDate(String date);
}