package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.AuthorFilterDTO;
import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    AuthorResponseDTO create(AuthorRequestDTO dto);
    AuthorResponseDTO findByName(String name);
    Page<AuthorResponseDTO> findAll(AuthorFilterDTO dto, Pageable pageable);
    void deleteByName(String name);
    void updateByName(AuthorRequestDTO dto);
}
