package io.library.api.application.services.impl;

import io.library.api.adapter.DTOs.requests.AuthorFilterDTO;
import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.adapter.mappers.AuthorMapper;
import io.library.api.adapter.repositories.AuthorRepository;
import io.library.api.adapter.repositories.specifications.AuthorSpecification;
import io.library.api.application.services.exceptions.ResourceHasDependencies;
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

    @Override
    @Transactional
    public AuthorResponseDTO create(AuthorRequestDTO dto) {
        Author newAuthor = authorMapper.toDomain(dto);

        if (authorRepository.findOne(AuthorSpecification.nameLike(newAuthor.getName())).isPresent()) {
            throw new ResourceAlreadyExistsException("Author já registrado.");
        }
        newAuthor = authorRepository.save(newAuthor);
        return authorMapper.toDTO(newAuthor);
    }

    @Override
    public Author findByName(String name) {
        return authorRepository.findOne(AuthorSpecification.nameLike(name))
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado nos registros."));
    }

    @Override
    public AuthorResponseDTO findByNameToDTO(String name) {
        return authorMapper.toDTO(findByName(name));
    }

    @Override
    public Page<AuthorResponseDTO> findAll(AuthorFilterDTO dto, Pageable pageable) {
        return authorRepository.findAll(AuthorSpecification.filterSpecification(dto), pageable)
                .map(authorMapper::toDTO);
    }


    @Override
    @Transactional
    public AuthorResponseDTO deleteByName(String name) {
        Author author = authorRepository.findOne(AuthorSpecification.nameLike(name))
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado nos registros."));

        if (!author.getBooks().isEmpty()) {
            throw new ResourceHasDependencies("Autor possui livros registrados.");
        }

        authorRepository.delete(author);
        return authorMapper.toDTO(author);
    }

    @Override
    @Transactional
    public AuthorResponseDTO updateByName(AuthorRequestDTO dto) {
        Author author = authorRepository.findOne(AuthorSpecification.nameLike(dto.name()))
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado nos registros."));
        authorMapper.updateAuthorFromDTO(dto, author);
        authorRepository.save(author);
        return authorMapper.toDTO(author);
    }

}
