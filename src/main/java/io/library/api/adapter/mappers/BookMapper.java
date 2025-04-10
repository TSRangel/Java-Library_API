package io.library.api.adapter.mappers;

import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.domain.entities.Book;
import io.library.api.domain.valueObjects.ISBN;
import io.library.api.domain.valueObjects.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    Book toDomain(BookRequestDTO dto);
    BookResponseDTO toDTO(Book book);

    default String map(ISBN isbn) {
        return isbn.value();
    }

    default ISBN map(String isbn) {
        return new ISBN(isbn);
    }

    default Double map(Price price) {
        return price.value();
    }

    default Price map(Double price) {
        return new Price(price);
    }
}
