package ru.sergalas.security.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sergalas.security.data.UserCreateRecord;
import ru.sergalas.security.data.UserUpdateRecord;
import ru.sergalas.security.service.UserService;
import ru.sergalas.security.web.helper.ControllerHelper;
import ru.sergalas.security.web.payload.ResponsePayload;

@RequiredArgsConstructor
@RestController
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    @PostMapping("create")
    public ResponseEntity<ResponsePayload> createUser(@Valid @RequestBody UserCreateRecord userCreateRecord) {
         userService.createUser(userCreateRecord);
         return ControllerHelper.isOk();
    }

    @PostMapping("update/{id}")
    public ResponseEntity<ResponsePayload> updateUser(@PathVariable String id,  @Valid @RequestBody UserUpdateRecord userUpdateRecord) {
       userService.updateUser(id, userUpdateRecord);
        return ControllerHelper.isOk();
    }
}

