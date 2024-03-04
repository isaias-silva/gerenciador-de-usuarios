package com.zack.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(
        @Size(min = 3, message = "nome muito curto, use no mínimo 4 dígitos") @Size(max = 21, message = "nome muito grande, use no máximo 20 dígitos")
        String name,
        @Email(message = "email inválido")
        String mail,
        @Size(min=11,message = "descrição muito curta use no mínimo 12 caracteres")
        @Size(max=201,message = "descrição muito longa use no máximo 200 caracteres")

        StringBuilder resume

) {
}
