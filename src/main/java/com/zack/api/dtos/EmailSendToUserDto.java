package com.zack.api.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EmailSendToUserDto(
        @NotNull(message = "id do usuário é requerido")
        UUID userId,
        @NotNull(message = "assunto é requerido")
        String subject,

        @NotNull(message = "conteúdo é requerido")

        String content) {
}
