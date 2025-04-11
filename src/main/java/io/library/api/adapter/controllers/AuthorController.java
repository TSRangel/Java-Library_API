package io.library.api.adapter.controllers;

import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.application.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<Void> createAuthor(@RequestBody AuthorRequestDTO request) {
        authorService.create(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("id/{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable String id) {
        return ResponseEntity.ok().body(authorService.findById(id));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<AuthorResponseDTO> getAuthorByName(@PathVariable String name) {
        return ResponseEntity.ok().body(authorService.findByName(name));
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAll() {
        return ResponseEntity.ok().body(authorService.findAll());
    }
}
