package io.library.api.adapter.controllers;

import io.library.api.adapter.DTOs.requests.BookFilterDTO;
import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.application.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
    public ResponseEntity<Page<BookResponseDTO>> getAll(@ModelAttribute BookFilterDTO request,
                                                        @PageableDefault(size = 10, page = 0, sort = "title",
                                                                direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(bookService.findAll(request, pageable));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookResponseDTO> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok().body(bookService.findByIsbn(isbn));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> delete(@PathVariable String isbn) {
        bookService.deleteByIsbn(isbn);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid BookRequestDTO request) {
        bookService.updateByIsbn(request);
        return ResponseEntity.noContent().build();
    }
}
