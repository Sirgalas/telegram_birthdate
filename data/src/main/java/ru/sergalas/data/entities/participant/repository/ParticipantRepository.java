package ru.sergalas.data.entities.participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sergalas.data.entities.participant.entity.Participant;

import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
}