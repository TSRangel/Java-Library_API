package io.library.api.adapter.mappers;

import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.domain.entities.Book;
import io.library.api.domain.valueObjects.ISBN;
import io.library.api.domain.valueObjects.Price;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    Book toDomain(BookRequestDTO dto);
    BookResponseDTO toDTO(Book book);
    void updateBookFromDTO(BookRequestDTO dto, @MappingTarget Book book);

    default String map(ISBN isbn) {
        return isbn.value();
    }

    default ISBN map(String isbn) {
        return new ISBN(isbn);
    }

    default BigDecimal map(Price price) {
        return price.value();
    }

    default Price map(BigDecimal price) {
        return new Price(price);
    }
}
