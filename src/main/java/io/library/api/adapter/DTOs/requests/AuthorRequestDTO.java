package io.library.api.adapter.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AuthorRequestDTO(
        @NotBlank(message = "Nome é um campo obrigatório")
        String name,
        @Past(message = "Data de nascimento não pode ser uma data futura.")
        @NotNull(message = "Data de nascimento é um campo obrigatório")
        LocalDate birthDate,
        @NotBlank(message = "Nacionalidade é um campo obrigatório")
        String nationality
) {}
