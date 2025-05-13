package io.library.api.adapter.mappers;

import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.domain.entities.Author;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {BookMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {
    Author toDomain(AuthorRequestDTO dto);
    Author toDomainFromResponseDTO(AuthorResponseDTO dto);
    AuthorResponseDTO toDTO(Author author);
    void updateAuthorFromDTO(AuthorRequestDTO dto, @MappingTarget Author author);
}
