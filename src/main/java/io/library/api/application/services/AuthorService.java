package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.adapter.mappers.AuthorMapper;
import io.library.api.application.repositories.AuthorRepository;
import io.library.api.domain.entities.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public Author create(AuthorRequestDTO dto) {
        Author newAuthor = authorMapper.toDomain(dto);
        authorRepository.save(newAuthor);
        return newAuthor;
    }

    public AuthorResponseDTO findById(String id) {
        Author author = authorRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado nos registros."));
        return authorMapper.toDTO(author);
    }

    public AuthorResponseDTO findByName(String name) {
        Author author = authorRepository.findByNameContaining(name)
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado nos registros."));
        return authorMapper.toDTO(author);
    }

    public Set<AuthorResponseDTO> findAll() {
        return authorRepository.findAll().stream().map(authorMapper::toDTO).collect(Collectors.toSet());
    }
}
