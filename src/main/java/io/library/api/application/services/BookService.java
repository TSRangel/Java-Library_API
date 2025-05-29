package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.BookFilterDTO;
import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.domain.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDTO create(BookRequestDTO dto);
    Book findByIsbn(String isbn);
    BookResponseDTO findByIsbnToDTO(String isbn);
    Page<BookResponseDTO> findAll(BookFilterDTO dto, Pageable pageable);
    BookResponseDTO deleteByIsbn(String isbn);
    BookResponseDTO updateByIsbn(BookRequestDTO dto);
}
