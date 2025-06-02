package ru.sergalas.data.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sergalas.data.entities.participant.data.ParticipantRequestCreatePayload;
import ru.sergalas.data.entities.participant.service.ParticipantService;
import ru.sergalas.data.web.payload.ResponsePayload;

@RestController
@RequestMapping("participant")
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping("create")
    public ResponseEntity<ResponsePayload> createParticipant(ParticipantRequestCreatePayload payload) {

        return new ResponseEntity<>(
            new ResponsePayload(
                HttpStatus.CREATED.value(),
                participantService.create(payload)
            ),
            HttpStatus.CREATED
        );
    }
}
