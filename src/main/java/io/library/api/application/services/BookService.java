package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.adapter.mappers.AuthorMapper;
import io.library.api.adapter.mappers.BookMapper;
import io.library.api.application.repositories.BookRepository;
import io.library.api.domain.entities.Author;
import io.library.api.domain.entities.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;

    public Book create(BookRequestDTO request) {
        Author author = authorMapper.toDomainFromResponseDTO(authorService.findByName(request.authorName()));
        Book newBook = bookMapper.toDomain(request);
        newBook.setAuthor(author);
        bookRepository.save(newBook);
        return newBook;
    }

    public BookResponseDTO findById(String id) {
        Book book = bookRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado nos registros."));
        return bookMapper.toDTO(book);
    }

    public BookResponseDTO findByTitle(String title) {
        Book book = bookRepository.findByTitleContaining(title)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado nos registros."));
        return bookMapper.toDTO(book);
    }

    public Set<BookResponseDTO> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDTO).collect(Collectors.toSet());
    }
}
