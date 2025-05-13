package io.library.api.adapter.controllers;

import io.library.api.adapter.DTOs.requests.RoleRequestDTO;
import io.library.api.adapter.DTOs.responses.RoleResponseDTO;
import io.library.api.application.services.RoleService;
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
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("roles")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody @Valid RoleRequestDTO request) {
        RoleResponseDTO role = roleService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(role.name()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<RoleResponseDTO> getByName(@PathVariable String name) {
        return ResponseEntity.ok().body(roleService.findByName(name));
    }

    @GetMapping
    public ResponseEntity<Page<RoleResponseDTO>> getAll(@PageableDefault(
            size = 10, page = 0, sort = "name",
            direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(roleService.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRole(@PathVariable UUID id, @RequestBody @Valid RoleRequestDTO request) {
        roleService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteByName(@PathVariable String name) {
        roleService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }
}

