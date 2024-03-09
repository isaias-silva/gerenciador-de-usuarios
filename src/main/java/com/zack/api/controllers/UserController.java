package com.zack.api.controllers;


import com.zack.api.dtos.*;
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
    public ResponseEntity<Object> register(@RequestBody @Valid UserCreateDto userCreateRecordDto) throws IOException {
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
    @GetMapping("/send/newCode")
    public ResponseEntity<Object> sendNewCode() throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.sendNewCode());
    }

    @GetMapping("/redefine/password")

    public ResponseEntity<Object> forgottenPass() throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.forgottenPassword());
    }
    @PutMapping("/update/password")
    public ResponseEntity<Object> updatePassword(@RequestBody @Valid ChangePasswordDto body) throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.changePassword(body));
    }
    @PutMapping("/update/password/forgotten")
    public ResponseEntity<Object> updatePasswordForgotten(@RequestBody @Valid ChangePasswordForgottenDto body) throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.changeForgottenPassword(body));
    }

    @PutMapping("/update/confirm/mail")
    public ResponseEntity<Object> confirmNewMail(@RequestBody @Valid ValidateCodeDto body) throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.confirmNewMail(body.code()));
    }

    @PutMapping("/validate")
    public ResponseEntity<Object> validateMail(@RequestBody @Valid ValidateCodeDto body) throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.validateMail(body.code()));
    }


    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody @Valid UserUpdateDto userUpdateDto) throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.updateUser(userUpdateDto));
    }

    @PutMapping("/update/profile")
    public ResponseEntity<Object> updateProfile(@RequestParam("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok().body(uploadService.generateFileProfileInServer(file.getInputStream(), file.getOriginalFilename()));

    }

}
