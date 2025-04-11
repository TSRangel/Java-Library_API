package io.library.api.adapter.controllers;

import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.application.services.BookService;
import io.library.api.domain.entities.Book;
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
    public ResponseEntity<Void> createBookByAuthor(@RequestBody BookRequestDTO request) {
        Book newBook = bookService.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id/{id}")
                .buildAndExpand(newBook.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<Set<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok().body(bookService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable String id) {
        return ResponseEntity.ok().body(bookService.findById(id));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<BookResponseDTO> getBookByTitle(@PathVariable String title) {
        return ResponseEntity.ok().body(bookService.findByTitle(title));
    }
}
