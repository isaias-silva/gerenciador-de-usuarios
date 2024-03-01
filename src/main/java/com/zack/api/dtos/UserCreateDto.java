package com.zack.api.dtos;


import jakarta.validation.constraints.*;

public record UserCreateDto(
        @Size(min = 3, message = "nome muito curto, use no mínimo 4 dígitos") @Size(max = 21, message = "nome muito grande, use no máximo 20 dígitos") @NotBlank(message = "name está vazio") String name,
        @NotNull(message = "email está vazio") @Email(message = "email inválido") String mail,
        @NotNull(message = "senha está vazia") @Size(min = 7, message = "senha muito curta, use no mínimo 8 dígitos") @Size(max = 13, message = "senha muito grande, use no máximo 12 dígitos") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "senha deve conter letras, números e símbolos")


        String password) {
}
