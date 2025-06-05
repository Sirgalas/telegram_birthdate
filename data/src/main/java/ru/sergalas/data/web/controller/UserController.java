package ru.sergalas.data.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sergalas.data.anatation.ValidateBindingResult;
import ru.sergalas.data.entities.user.data.UserRequestData;
import ru.sergalas.data.entities.user.data.UserRequestFindData;
import ru.sergalas.data.entities.user.service.UserService;
import ru.sergalas.data.web.payload.ResponsePayload;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("create")
    @ValidateBindingResult
    public ResponseEntity<ResponsePayload> createUser(@Valid @RequestBody UserRequestData data, BindingResult bindingResult) {

        return new ResponseEntity<>(
            new ResponsePayload(
                HttpStatus.CREATED.value(),
                userService.createUser(data)
            ),
            HttpStatus.CREATED
        );
    }

    @PostMapping("find")
    @ValidateBindingResult
    public ResponseEntity<ResponsePayload> findUser(@Valid @RequestBody UserRequestFindData data, BindingResult bindingResult) {
        return new ResponseEntity<>(
            new ResponsePayload(
                HttpStatus.OK.value(),
                userService.getUserByUserName(data.getUsername())
            ),
            HttpStatus.OK
        );
    }
}
