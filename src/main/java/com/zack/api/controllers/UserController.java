package com.zack.api.controllers;


import com.zack.api.dtos.UserCreateDto;
import com.zack.api.dtos.UserLoginDto;
import com.zack.api.services.UserServices;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServices userServices;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserCreateDto userCreateRecordDto) throws BadRequestException {
        return ResponseEntity.status(201).body(this.userServices.registerUser(userCreateRecordDto));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto) throws AccountNotFoundException, BadRequestException {
        return ResponseEntity.ok().body(this.userServices.loginUser(userLoginDto));
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getMe(@RequestBody @Valid UserLoginDto userLoginDto) throws AccountNotFoundException, BadRequestException {

        return ResponseEntity.ok().body(this.userServices.getMe());
    }
}
