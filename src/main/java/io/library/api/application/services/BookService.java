package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.adapter.mappers.AuthorMapper;
import io.library.api.adapter.mappers.BookMapper;
import io.library.api.application.repositories.AuthorRepository;
import io.library.api.application.repositories.BookRepository;
import io.library.api.domain.entities.Author;
import io.library.api.domain.entities.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;

    public void create(BookRequestDTO request) {
        Author author = authorMapper.toDomainFromResponseDTO(authorService.findById(request.authorId()));
        Book newBook = bookMapper.toDomain(request);
        newBook.setAuthor(author);
        bookRepository.save(newBook);
    }

    public BookResponseDTO findById(String id) {
        Book book = bookRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado nos registros."));
        return bookMapper.toDTO(book);
    }

    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDTO).toList();
    }
}
