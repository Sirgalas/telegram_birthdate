package ru.sergalas.security.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sergalas.security.service.UserService;
import ru.sergalas.security.web.helper.ControllerHelper;
import ru.sergalas.security.web.payload.ResponsePayload;
import jakarta.ws.rs.NotFoundException;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @GetMapping("index")
    public ResponseEntity<ResponsePayload> index(@PathVariable String userName, @PathVariable Integer first, @PathVariable Integer count){
        return ControllerHelper.isSuccess(HttpStatus.OK,userService.getAllUser(userName,first,count));
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<ResponsePayload> oneUser(@PathVariable String userId){
        try{
            return ControllerHelper.isSuccess(HttpStatus.OK,userService.getUser(userId));
        } catch (NotFoundException e) {
            return ControllerHelper.isError(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
}
