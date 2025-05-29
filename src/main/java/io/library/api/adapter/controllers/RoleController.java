package io.library.api.adapter.controllers;

import io.library.api.adapter.DTOs.requests.RoleRequestDTO;
import io.library.api.adapter.DTOs.responses.RoleResponseDTO;
import io.library.api.adapter.exceptionsHandlers.errors.StandartError;
import io.library.api.application.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("roles")
@Tag(name = "Roles", description = "Endpoints for managing roles")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Create a new role.", description = "Allows managers to create a new role.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Role created successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResponseDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Validation error.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            )),
            @ApiResponse(responseCode = "409", description = "Role already exists.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody @Valid RoleRequestDTO request) {
        RoleResponseDTO role = roleService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(role.name()).toUri();
        return ResponseEntity.created(location).body(role);
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Delete a role by name.", description = "Allows managers to delete a role by its name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role deleted successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Role not found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<RoleResponseDTO> deleteByName(@PathVariable String name) {
        RoleResponseDTO role = roleService.deleteByName(name);
        return ResponseEntity.ok().body(role);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Update an existing role.", description = "Allows managers to update an existing role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role updated successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Role not found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable UUID id, @RequestBody @Valid RoleRequestDTO request) {
        RoleResponseDTO role = roleService.update(id, request);
        return ResponseEntity.ok().body(role);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    @Operation(summary = "Get all roles.", description = "Allows managers and employees to retrieve a paginated list of all roles.")
    @Parameters({
            @Parameter(ref = "#/components/parameters/page"),
            @Parameter(ref = "#/components/parameters/size"),
            @Parameter(ref = "#/components/parameters/sort")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Roles retrieved successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "No roles found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<Page<RoleResponseDTO>> getAll(@Parameter(hidden = true) @PageableDefault(
            size = 10, page = 0, sort = "name",
            direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(roleService.findAll(pageable));
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    @Operation(summary = "Get a role by name.", description = "Allows managers and employees to retrieve a role by its name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role found successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Role not found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<RoleResponseDTO> getByName(@PathVariable String name) {
        return ResponseEntity.ok().body(roleService.findByNameToDTO(name));
    }
}

