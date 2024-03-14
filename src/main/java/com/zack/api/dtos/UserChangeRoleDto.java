package com.zack.api.dtos;

import com.zack.api.roles.UserRole;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserChangeRoleDto(
        @NotNull(message = "id é requerido.")
        UUID id,
        @NotNull(message="role é requerido")
        UserRole role,
        @NotNull(message = "subject é requerido")
        String subject,
        @NotNull(message = "message é requerido")
        String message

        ) {
}
