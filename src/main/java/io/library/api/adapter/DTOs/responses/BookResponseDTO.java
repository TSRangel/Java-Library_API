package io.library.api.adapter.DTOs.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.library.api.domain.enums.Genre;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record BookResponseDTO(
        UUID id,
        String isbn,
        String title,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT")
        LocalDate publicationDate,
        Genre genre,
        Double price,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
        LocalDateTime updatedAt
) {}
