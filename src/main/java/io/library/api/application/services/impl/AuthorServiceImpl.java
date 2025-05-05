package io.library.api.application.services.impl;

import io.library.api.adapter.DTOs.requests.AuthorFilterDTO;
import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.adapter.mappers.AuthorMapper;
import io.library.api.adapter.repositories.AuthorRepository;
import io.library.api.adapter.repositories.specifications.AuthorSpecification;
import io.library.api.domain.entities.Author;
import io.library.api.application.services.AuthorService;
import io.library.api.application.services.exceptions.ResourceAlreadyExistsException;
import io.library.api.application.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Transactional
    public AuthorResponseDTO create(AuthorRequestDTO dto) {
        Author newAuthor = authorMapper.toDomain(dto);

        if(authorRepository.findByNameContaining(newAuthor.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Author já registrado.");
        }
        authorRepository.save(newAuthor);
        return authorMapper.toDTO(newAuthor);
    }

    public AuthorResponseDTO findByName(String name) {
        Author author = authorRepository.findByNameContaining(name)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado nos registros."));
        return authorMapper.toDTO(author);
    }

    public Page<AuthorResponseDTO> findAll(AuthorFilterDTO dto, Pageable pageable) {
        return authorRepository.findAll(AuthorSpecification.filterSpecification(dto), pageable)
                .map(authorMapper::toDTO);
    }

    @Transactional
    public void deleteByName(String name) {
        Optional<Author> author = authorRepository.findByNameContaining(name);

        if(author.isEmpty()) {
            throw new ResourceNotFoundException("Autor não encontrado nos registros.");
        }

        authorRepository.delete(author.get());
    }

    @Transactional
    public void updateByName(AuthorRequestDTO dto) {
        Author author = authorRepository.findByNameContaining(dto.name())
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado nos registros."));
        authorMapper.updateAuthorFromDTO(dto, author);
        authorRepository.save(author);
    }

}
