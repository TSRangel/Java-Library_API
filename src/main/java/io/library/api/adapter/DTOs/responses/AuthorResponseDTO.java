package io.library.api.adapter.DTOs.responses;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record AuthorResponseDTO(
        UUID id,
        String name,
        LocalDate birthDate,
        String nationality,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Set<BookResponseDTO> books
) {}
