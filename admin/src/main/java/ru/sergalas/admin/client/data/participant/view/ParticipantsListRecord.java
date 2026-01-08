package ru.sergalas.admin.client.data.participant.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ParticipantsListRecord(
        List<OneParticipantData> participants
) {
}
