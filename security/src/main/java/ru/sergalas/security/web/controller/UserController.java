package ru.sergalas.security.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ResponsePayload> index(
            @RequestParam(name="user_name", required = false)String userName,
            @RequestParam(name="first", required = false) Integer first,
            @RequestParam(name="count", required = false) Integer count
    ){
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

    @GetMapping("roles")
    public ResponseEntity<ResponsePayload> roles() {
        return ControllerHelper.isSuccess(HttpStatus.OK,userService.getAllRealmRoles());
    }
}
