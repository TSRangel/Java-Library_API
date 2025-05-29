package io.library.api.adapter.controllers;

import io.library.api.adapter.DTOs.requests.AuthorFilterDTO;
import io.library.api.adapter.DTOs.requests.AuthorRequestDTO;
import io.library.api.adapter.DTOs.responses.AuthorResponseDTO;
import io.library.api.adapter.exceptionsHandlers.errors.FieldError;
import io.library.api.adapter.exceptionsHandlers.errors.StandartError;
import io.library.api.application.services.AuthorService;
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
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name = "Authors", description = "Endpoints for managing authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Create a new author.", description = "Allows managers to create a new author.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Author created successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthorResponseDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Validation error.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            )),
            @ApiResponse(responseCode = "409", description = "Author already exists.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<AuthorResponseDTO> createAuthor(@RequestBody @Valid AuthorRequestDTO request) {
        AuthorResponseDTO newAuthor = authorService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}").buildAndExpand(newAuthor.name()).toUri();
        return ResponseEntity.created(location).body(newAuthor);
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Delete an author.", description = "Allows managers to delete an author.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author deleted successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthorResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Author not found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            )),
            @ApiResponse(responseCode = "409", description = "Author has books registered.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<AuthorResponseDTO> delete(@PathVariable String name) {
        return ResponseEntity.ok().body(authorService.deleteByName(name));
    }

    @PutMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Update an author.", description = "Allows managers to update an existing author.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author updated successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthorResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Author not found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            )),
            @ApiResponse(responseCode = "400", description = "Validation error.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<AuthorResponseDTO> update(@RequestBody @Valid AuthorRequestDTO request) {
        return ResponseEntity.ok().body(authorService.updateByName(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    @Operation(summary = "Get all authors.", description = "Allows managers and employees to retrieve a paginated list of all authors.")
    @Parameters({
            @Parameter(ref = "#/components/parameters/page"),
            @Parameter(ref = "#/components/parameters/size"),
            @Parameter(ref = "#/components/parameters/sort")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authors found successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthorResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "No author found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            )),
            @ApiResponse(responseCode = "400", description = "Validation error.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FieldError.class)
            ))
    })
    public ResponseEntity<Page<AuthorResponseDTO>> getAll(@ParameterObject @ModelAttribute AuthorFilterDTO request,
                                                          @Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = "name",
                                                                  direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(authorService.findAll(request, pageable));
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    @Operation(summary = "Get an author by name.", description = "Allows managers ans employees to retrieve a author by his name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author found successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthorResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Author not found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<AuthorResponseDTO> getAuthorByName(@PathVariable String name) {
        return ResponseEntity.ok().body(authorService.findByNameToDTO(name));
    }
}
