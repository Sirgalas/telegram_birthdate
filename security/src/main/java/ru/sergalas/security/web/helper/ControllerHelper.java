package ru.sergalas.security.web.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.sergalas.security.web.data.ResponseData;
import ru.sergalas.security.web.data.SuccessData;
import ru.sergalas.security.web.payload.ResponsePayload;

public class ControllerHelper {
    public static ResponseEntity<ResponsePayload> isOk() {
        return new ResponseEntity<>(
            new ResponsePayload(
                200,
                new SuccessData()
            ),
            HttpStatus.OK
        );
    }

    public static ResponseEntity<ResponsePayload> isSuccess(HttpStatus status, ResponseData data) {
        return new ResponseEntity<>(
                new ResponsePayload(status.value(),data),
                status
        );
    }

    public static ResponseEntity<ResponsePayload> isError(HttpStatus status, String error) {
        return new ResponseEntity<>(
            new ResponsePayload(status.value(),error),
            status);
    }
}
