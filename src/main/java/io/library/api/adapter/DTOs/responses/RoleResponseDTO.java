package io.library.api.adapter.DTOs.responses;

import java.util.Set;
import java.util.UUID;

public record RoleResponseDTO(
        UUID id,
        String name
) {
}
