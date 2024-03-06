package com.zack.api.controllers;


import com.zack.api.dtos.UserCreateDto;
import com.zack.api.dtos.UserLoginDto;
import com.zack.api.dtos.UserUpdateDto;
import com.zack.api.services.UploadService;
import com.zack.api.services.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userServices;
    @Autowired
    UploadService uploadService;



    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserCreateDto userCreateRecordDto) throws BadRequestException {
        return ResponseEntity.status(201).body(this.userServices.registerUser(userCreateRecordDto));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto) throws AccountNotFoundException, BadRequestException {
        return ResponseEntity.ok().body(this.userServices.loginUser(userLoginDto));
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getMe() throws AccountNotFoundException, BadRequestException {
        return ResponseEntity.ok().body(this.userServices.getMe());
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody @Valid UserUpdateDto userUpdateDto) throws AccountNotFoundException, BadRequestException {
        return ResponseEntity.ok().body(this.userServices.updateUser(userUpdateDto));
    }

    @PutMapping("/update/profile")
    public ResponseEntity<Object> updateProfile( @RequestParam("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok().body(uploadService.generateFileProfileInServer(file.getInputStream(),file.getOriginalFilename()));

    }
}
