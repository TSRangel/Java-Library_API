package io.library.api.adapter.controllers;

import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.application.services.AuthorService;
import io.library.api.domain.entities.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<Void> createAuthor(@RequestBody AuthorRequestDTO request) {
        Author newAuthor = authorService.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id/{id}")
                .buildAndExpand(newAuthor.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable String id) {
        return ResponseEntity.ok().body(authorService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<AuthorResponseDTO> getAuthorByName(@PathVariable String name) {
        return ResponseEntity.ok().body(authorService.findByName(name));
    }

    @GetMapping
    public ResponseEntity<Set<AuthorResponseDTO>> getAll() {
        return ResponseEntity.ok().body(authorService.findAll());
    }
}
