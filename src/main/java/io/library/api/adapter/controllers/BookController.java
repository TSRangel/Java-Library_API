package io.library.api.adapter.controllers;

import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.application.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Void> createBookByAuthor(@RequestBody BookRequestDTO request) {
        bookService.create(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok().body(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable String id) {
        return ResponseEntity.ok().body(bookService.findById(id));
    }
}
