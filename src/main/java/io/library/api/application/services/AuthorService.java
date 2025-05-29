package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.AuthorFilterDTO;
import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.domain.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    AuthorResponseDTO create(AuthorRequestDTO dto);
    Author findByName(String name);
    AuthorResponseDTO findByNameToDTO(String name);
    Page<AuthorResponseDTO> findAll(AuthorFilterDTO dto, Pageable pageable);
    AuthorResponseDTO deleteByName(String name);
    AuthorResponseDTO updateByName(AuthorRequestDTO dto);
}
