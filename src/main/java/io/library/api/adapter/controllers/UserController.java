package io.library.api.adapter.controllers;

import io.library.api.adapter.DTOs.requests.UserRequestDTO;
import io.library.api.adapter.DTOs.responses.UserResponseDTO;
import io.library.api.adapter.exceptionsHandlers.errors.StandartError;
import io.library.api.application.services.UserService;
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

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Create a new user.", description = "Allows managers to create a new user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Validation error.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            )),
            @ApiResponse(responseCode = "409", description = "User already exists.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            )),
    })
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO request) {
        UserResponseDTO newUser = userService.registerToDTO(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{login}").buildAndExpand(newUser.login()).toUri();

        return ResponseEntity.created(location).body(newUser);
    }

    @PutMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Update an existing user.", description = "Allows managers to update user details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "User not found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            )),
            @ApiResponse(responseCode = "400", description = "Validation error.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<UserResponseDTO> update(@RequestBody @Valid UserRequestDTO request) {
        UserResponseDTO user = userService.update(request);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{login}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Delete an existing user.", description = "Allows managers to delete an user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "User not found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            )),
            @ApiResponse(responseCode = "400", description = "Validation error.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<UserResponseDTO> deleteByLogin(@PathVariable String login) {
        UserResponseDTO user = userService.deleteByLogin(login);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{login}")
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    @Operation(summary = "Get an user by his login.", description = "Allows managers ans employees to retrieve a user by his login.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "User not found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<UserResponseDTO> getUserByLogin(@PathVariable String login) {
        return ResponseEntity.ok().body(userService.findByLoginToDTO(login));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    @Operation(summary = "Get all users.", description = "Allows managers and employees to retrieve a paginated list of all users.")
    @Parameters({
            @Parameter(ref = "#/components/parameters/page"),
            @Parameter(ref = "#/components/parameters/size"),
            @Parameter(ref = "#/components/parameters/sort")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users found successfully.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "No user found.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandartError.class)
            ))
    })
    public ResponseEntity<Page<UserResponseDTO>> getAll(@Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = "login",
            direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(userService.findAll(pageable));
    }
}
