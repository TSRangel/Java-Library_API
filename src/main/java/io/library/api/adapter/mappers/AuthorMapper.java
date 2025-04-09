package io.library.api.adapter.mappers;

import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.domain.entities.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface AuthorMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "books", ignore = true)
    Author toDomain(AuthorRequestDTO dto);
    @Mapping(target = "books", source = "books")
    Author toDomainFromResponseDTO(AuthorResponseDTO dto);
    AuthorResponseDTO toDTO(Author author);
}
