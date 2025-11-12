package ru.sergalas.admin.services;

import ru.sergalas.admin.entity.Participant;

import java.util.List;

public interface ParticipantService {
    Participant createParticipant(Participant participant);
    Participant updateParticipant(Participant participant);
    void deleteParticipant(Participant participant);
    List<Participant> getAllParticipants(String data);
}
