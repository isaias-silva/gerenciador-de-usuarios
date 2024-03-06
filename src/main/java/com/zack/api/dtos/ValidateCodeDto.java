package com.zack.api.dtos;

import jakarta.validation.constraints.NotNull;

public record ValidateCodeDto(
        @NotNull(message = "código é requerido.")
        String code
) {
}
