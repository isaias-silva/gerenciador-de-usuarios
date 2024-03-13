package com.zack.api.controllers;

import com.zack.api.dtos.EmailSendToUserDto;
import com.zack.api.models.EmailModel;
import com.zack.api.services.AdmService;
import com.zack.api.services.EmailService;
import com.zack.api.services.UserService;
import com.zack.api.util.exceptions.NotFoundException;
import com.zack.api.util.responses.bodies.MailData;
import com.zack.api.util.responses.bodies.MailDataDetails;
import com.zack.api.util.responses.bodies.Response;
import com.zack.api.util.responses.bodies.UserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/adm")
public class AdmController {
    @Autowired
    AdmService admService;
    @Autowired
    EmailService mailService;
    @GetMapping("/users")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "busca usuários por página.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "dados dos usuários."),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação.", content = @Content(schema = @Schema(implementation = Response.class))),
                     @ApiResponse(responseCode = "403", description = "usuário não autorizado.", content = @Content(schema = @Schema(implementation = Response.class))),
            })
    public ResponseEntity<List<UserData>> getAll(@RequestParam(name = "page", required = true) int page, @RequestParam(name = "size", required = true) int size) throws NotFoundException {
        return ResponseEntity.ok().body(admService.getAllUsers(page,size));
    }
    @GetMapping("/users/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "busca usuário por id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "dados dos usuários."),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "404", description = "usuário não encontrado.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "403", description = "usuário não autorizado.", content = @Content(schema = @Schema(implementation = Response.class))),
            })
    public ResponseEntity<UserData> getOneUser(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok().body(admService.getUser(id));
    }


    @GetMapping("/emails")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "busca emails enviados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "retorna os emails."),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação.", content = @Content(schema = @Schema(implementation = Response.class))),
                     @ApiResponse(responseCode = "403", description = "usuário não autorizado.", content = @Content(schema = @Schema(implementation = Response.class))),
            })
    public ResponseEntity<List<MailData>> getMails(@RequestParam(name = "page", required = true) int page, @RequestParam(name = "size", required = true) int size) throws NotFoundException {
        return ResponseEntity.ok().body(mailService.getMails(page,size));
    }
    @GetMapping("/emails/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "busca emails enviados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "retorna um email com determinado id."),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "403", description = "usuário não autorizado.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "404", description = "e-mail não encontrado", content = @Content(schema = @Schema(implementation = Response.class))),

            })
    public ResponseEntity<MailDataDetails> getMail(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok().body(mailService.getMailDetails(id));
    }
    @PostMapping("/emails/send")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "envia e-mail para usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "email enviado."),
                    @ApiResponse(responseCode = "400", description = "erro no corpo da requisição.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "500", description = "erro interno na aplicação.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "403", description = "usuário não autorizado.", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "404", description = "usuário não encontrado", content = @Content(schema = @Schema(implementation = Response.class))),

            })
    public ResponseEntity<Response> sendMail(@RequestBody @Valid EmailSendToUserDto emailSendToUserDto) throws NotFoundException, IOException {
        return ResponseEntity.ok().body(admService.sendEmailToUser(emailSendToUserDto));
    }
}
