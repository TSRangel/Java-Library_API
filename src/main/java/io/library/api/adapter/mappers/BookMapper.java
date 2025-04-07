package io.library.api.adapter.mappers;

import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.domain.entities.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    void toDomain(BookRequestDTO dto);
    BookResponseDTO toDTO(Book book);
}
