package io.library.api.adapter.DTOs.responses;

import io.library.api.domain.enums.Genre;
import io.library.api.domain.valueObjects.ISBN;
import io.library.api.domain.valueObjects.Price;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record BookResponseDTO(
        UUID id,
        ISBN isbn,
        String title,
        LocalDate publicationDate,
        Genre genre,
        Price price,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
