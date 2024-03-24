package com.zack.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record UserUpdateDto(
        @Size(min = 3, message = "nome muito curto, use no mínimo 4 dígitos") @Size(max = 21, message = "nome muito grande, use no máximo 20 dígitos")
        String name,

        @Email(message = "e-mail inválido")
        String mail,

        @URL(message = "link  inválido")
        @Size(max = 80, message = "url do github muito longa")
        @Pattern(regexp = "(https://github\\.com/)([A-Za-z0-9_-]+)", message = "link do github inválido.")
        String githubURL,

        @URL(message = "link inválido")
        @Size(max = 80, message = "url do instagram muito longa")
        @Pattern(regexp = "(https://www\\.instagram\\.com/)([A-Za-z0-9_-]+)", message = "link do instagram inválido.")
        String instagramURL,

        @URL(message = "link do portfólio inválido")
        @Size(max = 80, message = "url do portfólio muito longa")
        String portfolioURL,

        @Size(min = 11, message = "descrição muito curta use no mínimo 12 caracteres")
        @Size(max = 201, message = "descrição muito longa use no máximo 200 caracteres")
        String resume

) {
}
