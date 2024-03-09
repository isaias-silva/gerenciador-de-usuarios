package com.zack.api.controllers;


import com.zack.api.dtos.*;
import com.zack.api.services.UploadService;
import com.zack.api.services.UserService;
import com.zack.api.util.responses.bodies.Response;
import com.zack.api.util.responses.bodies.ResponseJwt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;

@RestController("User")
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userServices;
    @Autowired
    UploadService uploadService;

    @PostMapping("/register")
    @Operation(description = "registra um novo usuário")
    public ResponseEntity<ResponseJwt> register(@RequestBody @Valid UserCreateDto userCreateRecordDto) throws IOException {
        return ResponseEntity.status(201).body(this.userServices.registerUser(userCreateRecordDto));
    }
    @PostMapping("/login")
    @Operation(description = "se autentifica em uma conta existente")
    public ResponseEntity<ResponseJwt> login(@RequestBody @Valid UserLoginDto userLoginDto) throws AccountNotFoundException, BadRequestException {
        return ResponseEntity.ok().body(this.userServices.loginUser(userLoginDto));
    }
    @PostMapping("/redefine/password")
    @Operation(description = "solicita e redefinição de senha")
    public ResponseEntity<Response> forgottenPass( @RequestBody @Valid RedefinePasswordDto body) throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.forgottenPassword(body));
    }
    @GetMapping("/me")
    @SecurityRequirement(name = "basicAuth")
    @Operation(description = "busca os dados do usuário autentificado")
    public ResponseEntity<Object> getMe() throws AccountNotFoundException, BadRequestException {
        return ResponseEntity.status(201).body(this.userServices.getMe());
    }
    @GetMapping("/send/newCode")
    @SecurityRequirement(name = "basicAuth")
    @Operation(description = "solicita um novo código de verificação")
    public ResponseEntity<Response> sendNewCode() throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.sendNewCode());
    }


    @PutMapping("/update/password")
    @SecurityRequirement(name = "basicAuth")
    @Operation(description = "troca a senha do usuário autentificado")
    public ResponseEntity<Response> updatePassword(@RequestBody @Valid ChangePasswordDto body) throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.changePassword(body));
    }
    @PutMapping("/change/password/forgotten")
    @Operation(description = "troca a senha de um usuário autentificado que esqueceu a senha")
    public ResponseEntity<Response> updatePasswordForgotten(@RequestBody @Valid ChangePasswordForgottenDto body) throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.changeForgottenPassword(body));
    }

    @PutMapping("/update/confirm/mail")
    @SecurityRequirement(name = "basicAuth")
    @Operation(description = "confirma a troca de e-mail de um usuário")
    public ResponseEntity<Response> confirmNewMail(@RequestBody @Valid ValidateCodeDto body) throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.confirmNewMail(body.code()));
    }

    @PutMapping("/validate")
    @SecurityRequirement(name = "basicAuth")
    @Operation(description = "válida o e-mail do usuário")
    public ResponseEntity<Response> validateMail(@RequestBody @Valid ValidateCodeDto body) throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.validateMail(body.code()));
    }


    @PutMapping("/update")
    @SecurityRequirement(name = "basicAuth")
    @Operation(description = "atualiza os dados de um usuário")
    public ResponseEntity<Response> update(@RequestBody @Valid UserUpdateDto userUpdateDto) throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.updateUser(userUpdateDto));
    }

    @PutMapping("/update/profile")
    @SecurityRequirement(name = "basicAuth")
    @Operation(description = "atualiza a imagem de perfil de um usuário")

    public ResponseEntity<Response> updateProfile(@RequestParam("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok().body(uploadService.generateFileProfileInServer(file.getInputStream(), file.getOriginalFilename()));

    }

}
