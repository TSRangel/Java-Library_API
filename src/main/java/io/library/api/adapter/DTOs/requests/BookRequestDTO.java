package io.library.api.adapter.DTOs.requests;

import io.library.api.domain.enums.Genre;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record BookRequestDTO(
        @NotBlank(message = "ISBN é um campo obrigatório")
        String isbn,
        @NotBlank(message = "Título é um campo obrigatório")
        String title,
        @PastOrPresent(message = "Data de publicação não pode ser uma data futura.")
        @NotNull(message = "Data de publicação é um campo obrigatório")
        LocalDate publicationDate,
        @NotNull(message = "Gênero é um campo obrigatório")
        Genre genre,
        @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser um valor positivo")
        @NotNull(message = "Preço é um campo obrigatório")
        Double price,
        @NotBlank(message = "Nome do autor é um campo obrigatório")
        String authorName
) {}
