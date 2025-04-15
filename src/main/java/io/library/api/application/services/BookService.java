package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.adapter.mappers.AuthorMapper;
import io.library.api.adapter.mappers.BookMapper;
import io.library.api.application.repositories.BookRepository;
import io.library.api.application.services.exceptions.ResourceAlreadyExistsException;
import io.library.api.application.services.exceptions.ResourceNotFoundException;
import io.library.api.domain.entities.Author;
import io.library.api.domain.entities.Book;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;

    @Transactional
    public BookResponseDTO create(BookRequestDTO request) {
        Book newBook = bookMapper.toDomain(request);

        if(bookRepository.findByTitleContaining(newBook.getTitle()).isPresent()) {
            throw new ResourceAlreadyExistsException("Livro já registrado.");
        }

        Author author = authorMapper.toDomainFromResponseDTO(authorService.findByName(request.authorName()));
        newBook.setAuthor(author);
        bookRepository.save(newBook);
        return bookMapper.toDTO(newBook);
    }

    public BookResponseDTO findByTitle(String title) {
        Book book = bookRepository.findByTitleContaining(title)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado nos registros."));
        return bookMapper.toDTO(book);
    }

    public Set<BookResponseDTO> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDTO).collect(Collectors.toSet());
    }

    @Transactional
    public void deleteByTitle(String title) {
        Optional<Book> book = bookRepository.findByTitleContaining(title);

        if(!book.isPresent()) {
            throw new ResourceNotFoundException("Livro não encontrado nos registros.");
        }

        bookRepository.delete(book.get());
    }

    @Transactional
    public void updateByName(BookRequestDTO dto) {
        Book book = bookRepository.findByTitleContaining(dto.title())
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado nos registros."));

        Book newBook = bookMapper.toDomain(dto);
        Author author = authorMapper.toDomainFromResponseDTO(authorService.findByName(dto.authorName()));
        newBook.setAuthor(author);
        newBook.setId(book.getId());
        bookRepository.save(newBook);
    }
}
