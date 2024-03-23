package com.zack.api.dtos;


import jakarta.validation.constraints.*;

public record UserCreateDto(
        @Size(min = 3, message = "nome muito curto, use no mínimo 4 dígitos") @Size(max = 11, message = "nome muito grande, use no máximo 10 dígitos") @NotBlank(message = "name está vazio") String name,
        @NotNull(message = "email está vazio") @Email(message = "email inválido") String mail,
        @NotNull(message = "senha está vazia")
        @Size(min = 7, message = "senha muito curta, use no mínimo 8 dígitos") @Size(max = 13, message = "senha muito grande, use no máximo 12 dígitos")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).+$", message = "senha deve conter letras, números e símbolos")


        String password) {
}
