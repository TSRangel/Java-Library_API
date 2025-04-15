package io.library.api.adapter.controllers;

import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.application.services.BookService;
import io.library.api.domain.entities.Book;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Void> createBookByAuthor(@RequestBody @Valid BookRequestDTO request) {
        BookResponseDTO newBook = bookService.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{title}")
                .buildAndExpand(newBook.title())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<Set<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok().body(bookService.findAll());
    }

    @GetMapping("/{title}")
    public ResponseEntity<BookResponseDTO> getBookByTitle(@PathVariable String title) {
        return ResponseEntity.ok().body(bookService.findByTitle(title));
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<Void> deleteByTitle(@PathVariable String title) {
        bookService.deleteByTitle(title);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid BookRequestDTO request) {
        bookService.updateByName(request);
        return ResponseEntity.noContent().build();
    }
}
