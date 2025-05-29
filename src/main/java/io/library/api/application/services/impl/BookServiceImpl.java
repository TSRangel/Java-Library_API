package io.library.api.application.services.impl;

import io.library.api.adapter.DTOs.requests.BookFilterDTO;
import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.adapter.mappers.AuthorMapper;
import io.library.api.adapter.mappers.BookMapper;
import io.library.api.adapter.repositories.BookRepository;
import io.library.api.adapter.repositories.specifications.BookSpecification;
import io.library.api.application.services.AuthorService;
import io.library.api.application.services.BookService;
import io.library.api.application.services.exceptions.ResourceAlreadyExistsException;
import io.library.api.application.services.exceptions.ResourceNotFoundException;
import io.library.api.domain.entities.Author;
import io.library.api.domain.entities.Book;
import io.library.api.domain.valueObjects.ISBN;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BookResponseDTO create(BookRequestDTO dto) {
        Book newBook = bookMapper.toDomain(dto);

        if(bookRepository.findByIsbn(new ISBN(dto.isbn())).isPresent()) {
            throw new ResourceAlreadyExistsException("Livro já registrado.");
        }

        Author author = authorService.findByName(dto.authorName());
        newBook.setAuthor(author);
        newBook = bookRepository.save(newBook);
        return bookMapper.toDTO(newBook);
    }

    @Override
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(new ISBN(isbn))
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado nos registros."));
    }

    @Override
    public BookResponseDTO findByIsbnToDTO(String isbn) {
        return bookMapper.toDTO(findByIsbn(isbn));
    }

    @Override
    public Page<BookResponseDTO> findAll(BookFilterDTO dto, Pageable pageable) {
        return bookRepository.findAll(BookSpecification.filterSpecification(dto), pageable)
                .map(bookMapper::toDTO);
    }

    @Override
    @Transactional
    public BookResponseDTO deleteByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(new ISBN(isbn))
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado nos registros."));

        bookRepository.delete(book);
        return bookMapper.toDTO(book);
    }

    @Override
    @Transactional
    public BookResponseDTO updateByIsbn(BookRequestDTO dto) {
        Book book = bookRepository.findByIsbn(new ISBN(dto.isbn()))
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado nos registros."));

        bookMapper.updateBookFromDTO(dto, book);
        Author author = authorService.findByName(dto.authorName());
        book.setAuthor(author);
        bookRepository.save(book);
        return bookMapper.toDTO(book);
    }
}
