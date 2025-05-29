package io.library.api.adapter.controllers;

import com.nimbusds.jwt.JWT;
import io.library.api.adapter.DTOs.requests.BookFilterDTO;
import io.library.api.adapter.DTOs.requests.BookRequestDTO;
import io.library.api.adapter.DTOs.responses.BookResponseDTO;
import io.library.api.adapter.exceptionsHandlers.errors.StandartError;
import io.library.api.application.services.BookService;
import io.library.api.domain.valueObjects.ISBN;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("books")
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Create a new book.", description = "Allows managers to create a new book.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Book created successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookResponseDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Validation error.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            )),
            @ApiResponse(responseCode = "409", description = "Book already exists.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            )),
            @ApiResponse(responseCode = "404", description = "Author not found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<BookResponseDTO> createBookByAuthor(@RequestBody @Valid BookRequestDTO request) {
        BookResponseDTO newBook = bookService.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{title}")
                .buildAndExpand(newBook.title())
                .toUri();
        return ResponseEntity.created(location).body(newBook);
    }

    @DeleteMapping("/{isbn}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Delete an book.", description = "Allows managers to delete a book by its ISBN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book deleted successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Book not found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<BookResponseDTO> deleteByIsbn(@ParameterObject @PathVariable String isbn) {
        BookResponseDTO deletedBook = bookService.deleteByIsbn(isbn);
        return ResponseEntity.ok().body(deletedBook);
    }

    @PutMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Update an book by its ISBN.", description = "Allows managers to update a book by its ISBN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book updated successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Book not found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            )),
            @ApiResponse(responseCode = "400", description = "Validation error.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            )),
    })
    public ResponseEntity<BookResponseDTO> updateByIsbn(@RequestBody @Valid BookRequestDTO request) {
        BookResponseDTO updatedBook = bookService.updateByIsbn(request);
        return ResponseEntity.ok().body(updatedBook);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    @Operation(summary = "Get all books.", description = "Allows managers and employees to retrieve a paginated list of all books.")
    @Parameters({
            @Parameter(ref = "#/components/parameters/page"),
            @Parameter(ref = "#/components/parameters/size"),
            @Parameter(ref = "#/components/parameters/sort")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books found successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "No book found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<Page<BookResponseDTO>> getAll(@ParameterObject @ModelAttribute BookFilterDTO request,
                                                        @Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = "title",
                                                                direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(bookService.findAll(request, pageable));
    }

    @GetMapping("/{isbn}")
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    @Operation(summary = "Get an book by its ISBN.", description = "Allows managers and employees to retrieve a book by its ISBN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book found successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "No book found.", content = @Content(
                     mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<BookResponseDTO> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok().body(bookService.findByIsbnToDTO(isbn));
    }
}
