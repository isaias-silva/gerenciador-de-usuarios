package com.zack.api.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record UserCreateDto(
        @NotBlank(message = "name está vazio")
        String name,
        @NotNull(message = "email está vazio")
        String mail,
        @NotNull(message = "senha está vazia") String password
) {
}
