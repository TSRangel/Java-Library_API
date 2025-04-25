package io.library.api.adapter.DTOs.requests;

import io.library.api.domain.enums.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class BookFilterDTO {
    private String title;
    private String author;
    private LocalDate publication;
    private Genre genre;
    private BigDecimal price;
}
