package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.BookFilterDTO;
import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.adapter.mappers.AuthorMapper;
import io.library.api.adapter.mappers.BookMapper;
import io.library.api.application.repositories.BookRepository;
import io.library.api.application.services.exceptions.ResourceAlreadyExistsException;
import io.library.api.application.services.exceptions.ResourceNotFoundException;
import io.library.api.application.specifications.BookSpecification;
import io.library.api.domain.entities.Author;
import io.library.api.domain.entities.Book;
import io.library.api.domain.valueObjects.ISBN;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        if(bookRepository.findByIsbn(new ISBN(request.isbn())).isPresent()) {
            throw new ResourceAlreadyExistsException("Livro já registrado.");
        }

        Author author = authorMapper.toDomainFromResponseDTO(authorService.findByName(request.authorName()));
        newBook.setAuthor(author);
        bookRepository.save(newBook);
        return bookMapper.toDTO(newBook);
    }

    public BookResponseDTO findByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(new ISBN(isbn))
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado nos registros."));
        return bookMapper.toDTO(book);
    }

    public Page<BookResponseDTO> findAll(BookFilterDTO dto, Pageable pageable) {
        return bookRepository.findAll(BookSpecification.filterSpecification(dto), pageable)
                .map(bookMapper::toDTO);
    }

    @Transactional
    public void deleteByIsbn(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(new ISBN(isbn));

        if(book.isEmpty()) {
            throw new ResourceNotFoundException("Livro não encontrado nos registros.");
        }

        bookRepository.delete(book.get());
    }

    @Transactional
    public void updateByIsbn(BookRequestDTO dto) {
        Book book = bookRepository.findByIsbn(new ISBN(dto.isbn()))
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado nos registros."));

        bookMapper.updateBookFromDTO(dto, book);
        Author author = authorMapper.toDomainFromResponseDTO(authorService.findByName(dto.authorName()));
        book.setAuthor(author);
        bookRepository.save(book);
    }
}
