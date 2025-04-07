package io.library.api.adapter.mappers;

import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.domain.entities.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface AuthorMapper {
    Author toDomain(AuthorRequestDTO dto);
    AuthorResponseDTO toDTO(Author author);
}
