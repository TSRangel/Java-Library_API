package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.AuthorFilterDTO;
import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.adapter.mappers.AuthorMapper;
import io.library.api.application.repositories.AuthorRepository;
import io.library.api.application.services.exceptions.ResourceAlreadyExistsException;
import io.library.api.application.services.exceptions.ResourceNotFoundException;
import io.library.api.application.specifications.AuthorSpecification;
import io.library.api.domain.entities.Author;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
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

    public Set<AuthorResponseDTO> findAll(AuthorFilterDTO dto) {
        return authorRepository.findAll(AuthorSpecification.filterSpecification(dto))
                .stream().map(authorMapper::toDTO).collect(Collectors.toSet());
    }

    @Transactional
    public void deleteByName(String name) {
        Optional<Author> author = authorRepository.findByNameContaining(name);

        if(!author.isPresent()) {
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
