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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public void create(AuthorRequestDTO dto) {
        authorRepository.save(authorMapper.toDomain(dto));
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

    public List<AuthorResponseDTO> findAll() {
        return authorRepository.findAll().stream().map(authorMapper::toDTO).toList();
    }
}
