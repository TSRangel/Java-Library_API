package io.library.api.adapter.DTOs.requests;

import io.library.api.domain.enums.Genre;
import io.library.api.domain.valueObjects.ISBN;
import io.library.api.domain.valueObjects.Price;

import java.time.LocalDate;

public record BookRequestDTO(
        ISBN isbn,
        String title,
        LocalDate publicationDate,
        Genre genre,
        Price price
) {}
