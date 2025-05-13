package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.RoleRequestDTO;
import io.library.api.adapter.DTOs.responses.RoleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RoleService {
    RoleResponseDTO create(RoleRequestDTO dto);
    RoleResponseDTO findByName(String name);
    Page<RoleResponseDTO> findAll(Pageable pageable);
    void update(UUID id,RoleRequestDTO dto);
    void deleteByName(String name);
}
