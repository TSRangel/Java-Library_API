package io.library.api.adapter.DTOs.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String login,
        Set<RoleResponseDTO> roles,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
        LocalDateTime updatedAt
) {
}
