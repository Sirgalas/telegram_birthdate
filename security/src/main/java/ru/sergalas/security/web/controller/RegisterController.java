package ru.sergalas.security.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sergalas.security.data.UserCreateRecord;
import ru.sergalas.security.data.UserUpdateRecord;
import ru.sergalas.security.service.UserService;
import ru.sergalas.security.web.helper.ControllerHelper;
import ru.sergalas.security.web.payload.ResponsePayload;

@RequiredArgsConstructor
@RestController
@RequestMapping("/registry")
public class RegisterController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponsePayload> createUser(@Valid @RequestBody UserCreateRecord userCreateRecord) {
         return ControllerHelper.isSuccess(HttpStatus.OK, userService.createUser(userCreateRecord));
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponsePayload> updateUser(@PathVariable String id,  @Valid @RequestBody UserUpdateRecord userUpdateRecord) {
        return ControllerHelper.isSuccess(HttpStatus.OK,userService.updateUser(id, userUpdateRecord));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponsePayload> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ControllerHelper.isOk();
    }
}

