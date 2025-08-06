package ru.sergalas.data.entities.participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sergalas.data.entities.participant.entity.Participant;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    @Query(value = """
        SELECT 
        p.*
        FROM participants p 
        INNER JOIN date_periodicity dp ON dp.id = p.date_periodicity_id
        WHERE  dp.date = :date 
    """, nativeQuery = true)
    List<Participant> getParticipantByDate(String date);
}