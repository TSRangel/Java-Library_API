package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.RoleRequestDTO;
import io.library.api.adapter.DTOs.responses.RoleResponseDTO;
import io.library.api.domain.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RoleService {
    RoleResponseDTO create(RoleRequestDTO dto);
    Role findByName(String name);
    RoleResponseDTO findByNameToDTO(String name);
    Page<RoleResponseDTO> findAll(Pageable pageable);
    RoleResponseDTO update(UUID id,RoleRequestDTO dto);
    RoleResponseDTO deleteByName(String name);
}
