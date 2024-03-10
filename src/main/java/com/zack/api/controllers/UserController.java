package com.zack.api.controllers;

import com.zack.api.services.UploadService;
import com.zack.api.services.UserService;
import com.zack.api.util.exceptions.NotFoundException;
import com.zack.api.util.responses.bodies.Response;
import com.zack.api.util.responses.bodies.ResponseJwt;
import com.zack.api.util.responses.bodies.UserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;

import com.zack.api.dtos.*;
@RestController()
@RequestMapping("/user")


public class UserController {

    @Autowired
    UserService userServices;
    @Autowired
    UploadService uploadService;

    @PostMapping("/register")
    @Operation(description = "registra um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "usuário registrado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição."),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação."),

            }
    )
    public ResponseEntity<ResponseJwt> register(@RequestBody @Valid UserCreateDto userCreateRecordDto) throws IOException {
        return ResponseEntity.status(201).body(this.userServices.registerUser(userCreateRecordDto));
    }

    @PostMapping("/login")
    @Operation(description = "se autentifica em uma conta existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "usuário logado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição."),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação."),

            })
    public ResponseEntity<ResponseJwt> login(@RequestBody @Valid UserLoginDto userLoginDto) throws BadRequestException {

        return ResponseEntity.ok().body(this.userServices.loginUser(userLoginDto));
    }

    @PostMapping("/redefine/password")
    @Operation(description = "solicita e redefinição de senha",
            responses = {
                    @ApiResponse(responseCode = "200", description = "solicitada a troca de senha."),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição."),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação."),

            })
    public ResponseEntity<Response> forgottenPass(@RequestBody @Valid RedefinePasswordDto body) throws AccountNotFoundException, IOException {
        return ResponseEntity.ok().body(this.userServices.forgottenPassword(body));
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "busca os dados do usuário autentificado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "dados do usuário."),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "404", description = "usuário não encontrado.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "403", description = "usuário não autorizado.", content = @Content(schema = @Schema(implementation = Response.class))),
            })
    public ResponseEntity<UserData> getMe() throws NotFoundException {
        return ResponseEntity.status(201).body(this.userServices.getMe());
    }

    @GetMapping("/send/newCode")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "solicita um novo código de verificação",
            responses = {
                    @ApiResponse(responseCode = "200", description = "novo código de verificação enviado."),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição."),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação."),
                    @ApiResponse(responseCode = "404", description = "usuário não encontrado."),
                    @ApiResponse(responseCode = "403", description = "usuário não autorizado."),
            }
    )
    public ResponseEntity<Response> sendNewCode() throws  IOException {
        return ResponseEntity.ok().body(this.userServices.sendNewCode());
    }


    @PutMapping("/update/password")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "troca a senha do usuário autentificado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "senha alterada"),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição."),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação."),
                    @ApiResponse(responseCode = "404", description = "usuário não encontrado."),
                    @ApiResponse(responseCode = "403", description = "usuário não autorizado."),
            })
    public ResponseEntity<Response> updatePassword(@RequestBody @Valid ChangePasswordDto body){
        return ResponseEntity.ok().body(this.userServices.changePassword(body));
    }

    @PutMapping("/change/password/forgotten")
    @Operation(description = "troca a senha de um usuário que esqueceu a senha.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "senha alterada"),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição."),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação."),
                    @ApiResponse(responseCode = "404", description = "usuário não encontrado."),
                    @ApiResponse(responseCode = "401", description = "código incorreto."),
            })

    public ResponseEntity<Response> updatePasswordForgotten(@RequestBody @Valid ChangePasswordForgottenDto body) throws IOException {
        return ResponseEntity.ok().body(this.userServices.changeForgottenPassword(body));
    }

    @PutMapping("/update/confirm/mail")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "confirma a troca de e-mail de um usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "email atualizado."),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição."),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação."),
                    @ApiResponse(responseCode = "404", description = "usuário não encontrado."),
                    @ApiResponse(responseCode = "403", description = "usuário não autorizado."),
                    @ApiResponse(responseCode = "401", description = "código incorreto."),

            })
    public ResponseEntity<Response> confirmNewMail(@RequestBody @Valid ValidateCodeDto body) {
        return ResponseEntity.ok().body(this.userServices.confirmNewMail(body.code()));
    }

    @PutMapping("/validate")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "válida o e-mail do usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "e-mail validado"),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição."),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação."),
                    @ApiResponse(responseCode = "404", description = "usuário não encontrado."),
                    @ApiResponse(responseCode = "403", description = "usuário não autorizado."),
                    @ApiResponse(responseCode = "401", description = "código incorreto."),

            })
    public ResponseEntity<Response> validateMail(@RequestBody @Valid ValidateCodeDto body) throws IOException {
        return ResponseEntity.ok().body(this.userServices.validateMail(body.code()));
    }


    @PutMapping("/update")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "atualiza os dados de um usuário",  responses = {
            @ApiResponse(responseCode = "200", description = "dados atualizados"),
            @ApiResponse(responseCode = "400", description = "erro no corpo da requisição."),
            @ApiResponse(responseCode = "500", description = "erro interno na aplicação."),
            @ApiResponse(responseCode = "404", description = "usuário não encontrado."),
            @ApiResponse(responseCode = "403", description = "usuário não autorizado."),

    })
    public ResponseEntity<Response> update(@RequestBody @Valid UserUpdateDto userUpdateDto) throws  IOException {
        return ResponseEntity.ok().body(this.userServices.updateUser(userUpdateDto));
    }

    @PutMapping("/update/profile")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "atualiza a imagem de perfil de um usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "perfil atualizado."),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição."),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação."),
                    @ApiResponse(responseCode = "404", description = "usuário não encontrado."),
                    @ApiResponse(responseCode = "403", description = "usuário não autorizado."),

            })

    public ResponseEntity<Response> updateProfile(@RequestParam("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok().body(uploadService.generateFileProfileInServer(file.getInputStream(), file.getOriginalFilename()));

    }

}
