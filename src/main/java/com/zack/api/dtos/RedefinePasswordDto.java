package com.zack.api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RedefinePasswordDto(


        @NotNull(message = "email é requerido")
        String email



) {
}
