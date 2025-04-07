package io.library.api.adapter.DTOs.requests;

import java.time.LocalDate;

public record AuthorRequestDTO(
        String name,
        LocalDate birthDate,
        String nationality
) {}
