package io.library.api.adapter.DTOs.requests;

import io.library.api.domain.enums.Genre;

import java.time.LocalDate;

public record BookRequestDTO(
        String isbn,
        String title,
        LocalDate publicationDate,
        Genre genre,
        Double price,
        String authorId
) {}
