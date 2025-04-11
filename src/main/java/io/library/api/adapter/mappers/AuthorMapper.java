package io.library.api.adapter.mappers;

import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.domain.entities.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {BookMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AuthorMapper {
    Author toDomain(AuthorRequestDTO dto);
    @Mapping(target = "books", source = "books")
    Author toDomainFromResponseDTO(AuthorResponseDTO dto);
    AuthorResponseDTO toDTO(Author author);
}
