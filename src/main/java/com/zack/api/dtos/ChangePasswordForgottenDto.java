package com.zack.api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangePasswordForgottenDto(
        @NotNull(message = "código é requerido")

        String code,

        @Size(min = 7, message = "senha muito curta, use no mínimo 8 dígitos") @Size(max = 13, message = "senha muito grande, use no máximo 12 dígitos")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).+$", message = "senha deve conter letras, números e símbolos")
        @NotNull(message = "nova senha é requerida")
        String newPassword
) {
}
