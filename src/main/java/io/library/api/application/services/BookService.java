package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.BookFilterDTO;
import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDTO create(BookRequestDTO dto);
    BookResponseDTO findByIsbn(String isbn);
    Page<BookResponseDTO> findAll(BookFilterDTO dto, Pageable pageable);
    void deleteByIsbn(String isbn);
    void updateByIsbn(BookRequestDTO dto);
}
