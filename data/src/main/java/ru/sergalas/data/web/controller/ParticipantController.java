package ru.sergalas.data.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sergalas.data.entities.participant.data.ParticipantRequestCreatePayload;
import ru.sergalas.data.entities.participant.data.ParticipantRequestUpdatePayload;
import ru.sergalas.data.entities.participant.exception.ParticipantNotFoundException;
import ru.sergalas.data.entities.participant.service.ParticipantService;
import ru.sergalas.data.web.payload.ResponsePayload;

import java.util.UUID;

@RestController
@RequestMapping("participant")
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping()
    public ResponseEntity<ResponsePayload> createParticipant(@Valid @RequestBody ParticipantRequestCreatePayload payload) {

        return new ResponseEntity<>(
            new ResponsePayload(
                HttpStatus.CREATED.value(),
                participantService.create(payload)
            ),
            HttpStatus.CREATED
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateParticipant(@Valid @RequestBody ParticipantRequestUpdatePayload payload, @PathVariable String id) {
        try{
            participantService.update(payload, UUID.fromString(id));
            return ResponseEntity.noContent().build();
        }catch (ParticipantNotFoundException e) {
            return new ResponseEntity<>(
                new ResponsePayload(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage()
                )
            , HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteParticipant(@PathVariable String id) {
        try {
            participantService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (ParticipantNotFoundException e) {
            return new ResponseEntity<>(
                new ResponsePayload(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage()
                )
                , HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    private ResponseEntity<ResponsePayload> getParticipant(@RequestParam(name = "date", required = false) String date) {
        try{
            return new ResponseEntity<>(
                new ResponsePayload(
                    HttpStatus.OK.value(),
                    participantService.getByDate(date)
                ),
                HttpStatus.OK
            );
        } catch (ParticipantNotFoundException e) {
            return new ResponseEntity<>(
                new ResponsePayload(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage()
                )
                , HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    private ResponseEntity<ResponsePayload> getOneParticipant(@RequestParam(name = "id") String id) {
        try{
            return new ResponseEntity<>(
                new ResponsePayload(
                    HttpStatus.OK.value(),
                    participantService.getById(id)
                ),
                HttpStatus.OK
            );
        }catch (ParticipantNotFoundException e) {
            return new ResponseEntity<>(
                    new ResponsePayload(
                            HttpStatus.NOT_FOUND.value(),
                            e.getMessage()
                    ),
                    HttpStatus.NOT_FOUND);
        }
    }
}
