package com.zack.api.dtos;


import jakarta.validation.constraints.*;

public record UserLoginDto(

        @Email(message = "email inválido") String mail , @NotNull(message="senha inválida") String password){

}
