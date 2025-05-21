package io.library.api.adapter.controllers;

import io.library.api.adapter.DTOs.requests.BookFilterDTO;
import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.application.services.BookService;
import io.library.api.domain.valueObjects.ISBN;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> createBookByAuthor(@RequestBody @Valid BookRequestDTO request) {
        BookResponseDTO newBook = bookService.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{title}")
                .buildAndExpand(newBook.title())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{isbn}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteByIsbn(@PathVariable String isbn) {
        bookService.deleteByIsbn(isbn);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{isbn}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> updateByIsbn(@RequestBody @Valid BookRequestDTO request) {
        bookService.updateByIsbn(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    public ResponseEntity<Page<BookResponseDTO>> getAll(@ModelAttribute BookFilterDTO request,
                                                        @PageableDefault(size = 10, page = 0, sort = "title",
                                                                direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(bookService.findAll(request, pageable));
    }

    @GetMapping("/{isbn}")
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    public ResponseEntity<BookResponseDTO> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok().body(bookService.findByIsbnToDTO(isbn));
    }
}
