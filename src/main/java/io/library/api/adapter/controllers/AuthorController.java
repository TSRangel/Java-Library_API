package io.library.api.adapter.controllers;

import io.library.api.adapter.DTOs.requests.AuthorFilterDTO;
import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.application.services.AuthorService;
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
@RequestMapping("authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> createAuthor(@RequestBody @Valid AuthorRequestDTO request) {
        AuthorResponseDTO newAuthor = authorService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}").buildAndExpand(newAuthor.name()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        authorService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> update(@RequestBody @Valid AuthorRequestDTO request) {
        authorService.updateByName(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    public ResponseEntity<Page<AuthorResponseDTO>> getAll(@ModelAttribute AuthorFilterDTO request,
                                                          @PageableDefault(size = 10, page = 0, sort = "name",
                                                                  direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(authorService.findAll(request, pageable));
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    public ResponseEntity<AuthorResponseDTO> getAuthorByName(@PathVariable String name) {
        return ResponseEntity.ok().body(authorService.findByNameToDTO(name));
    }
}
