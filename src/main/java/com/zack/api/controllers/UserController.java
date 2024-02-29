package com.zack.api.controllers;


import com.zack.api.dtos.UserCreateDto;
import com.zack.api.services.UserServices;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserCreateDto userCreateRecordDto) throws BadRequestException {
        return ResponseEntity.status(201).body(this.userServices.registerUser(userCreateRecordDto));
    }
}
