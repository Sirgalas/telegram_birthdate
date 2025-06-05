package ru.sergalas.data.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sergalas.data.entities.participant.exception.ParticipantNotFoundException;
import ru.sergalas.data.entities.periodicity.data.PeriodicityCreateRequestData;
import ru.sergalas.data.entities.periodicity.service.PeriodicityService;
import ru.sergalas.data.web.payload.ResponsePayload;

@RequiredArgsConstructor
@RestController
@RequestMapping("periodicity")
public class PeriodicityController {

    private final PeriodicityService periodicityService;

    @GetMapping("{date:\\s+}")
    public ResponseEntity<ResponsePayload> getByDate(@PathVariable("date") String date) {
        return new ResponseEntity<>(
                new ResponsePayload(
                        HttpStatus.OK.value(),
                        periodicityService.getToDate(date)
                ),
                HttpStatus.OK
        );
    }

    @PostMapping()
    public ResponseEntity<ResponsePayload> create(@Valid @RequestBody PeriodicityCreateRequestData data) {
        return new ResponseEntity<>(
            new ResponsePayload(
                HttpStatus.OK.value(),
                periodicityService.create(data)
            ),
            HttpStatus.OK
        );
    }

    @PutMapping("{id:\\s+}")
    public ResponseEntity<?> update(
            @Valid @RequestBody PeriodicityCreateRequestData data,
            @PathVariable("id") String id)
    {
        try {
            periodicityService.update(data,id);
            return ResponseEntity.noContent().build();
        } catch (ParticipantNotFoundException e) {
            return new ResponseEntity<>(
                    new ResponsePayload(
                            HttpStatus.NOT_FOUND.value(),
                            e.getMessage()
                    )
                    , HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("{id:\s+}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try{
            periodicityService.delete(id);
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
}
