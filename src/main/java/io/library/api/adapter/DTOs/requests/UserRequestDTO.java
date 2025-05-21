package io.library.api.adapter.DTOs.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserRequestDTO(
        @Email(message = "Login deve ser um e-mail válido")
        @NotBlank(message = "Login é um campo obrigatório")
        String login,
        @NotBlank(message = "Password é um campo obrigatório")
        String password,
        @NotNull(message = "Roles é um campo obrigatório")
        Set<String> roles
) {
}
