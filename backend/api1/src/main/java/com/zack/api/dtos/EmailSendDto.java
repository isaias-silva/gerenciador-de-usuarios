package com.zack.api.dtos;

import jakarta.validation.constraints.NotNull;

public record EmailSendDto(
        @NotNull(message = "destinatário é requerido")
        String to,
        @NotNull(message = "assunto é requerido")
        String subject,

        @NotNull(message = "conteúdo é requerido")

        String content) {
}
