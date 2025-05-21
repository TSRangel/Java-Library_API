package io.library.api.adapter.controllers;

import io.library.api.adapter.DTOs.requests.UserRequestDTO;
import io.library.api.adapter.DTOs.responses.UserResponseDTO;
import io.library.api.application.services.UserService;
import jakarta.persistence.PreUpdate;
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

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO request) {
        UserResponseDTO newUser = userService.registerToDTO(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{login}").buildAndExpand(newUser.login()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> update(@RequestBody @Valid UserRequestDTO request) {
        userService.update(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{login}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteByLogin(@PathVariable String login) {
        userService.deleteByLogin(login);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{login}")
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    public ResponseEntity<UserResponseDTO> getUserByLogin(@PathVariable String login) {
        return ResponseEntity.ok().body(userService.findByLoginToDTO(login));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    public ResponseEntity<Page<UserResponseDTO>> getAll(@PageableDefault(size = 10, page = 0, sort = "login",
            direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(userService.findAll(pageable));
    }
}
