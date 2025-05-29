package io.library.api.application.services.impl;

import io.library.api.adapter.DTOs.requests.RoleRequestDTO;
import io.library.api.adapter.DTOs.responses.RoleResponseDTO;
import io.library.api.adapter.mappers.RoleMapper;
import io.library.api.adapter.repositories.RoleRepository;
import io.library.api.application.services.RoleService;
import io.library.api.application.services.exceptions.ResourceAlreadyExistsException;
import io.library.api.application.services.exceptions.ResourceNotFoundException;
import io.library.api.domain.entities.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional
    @Override
    public RoleResponseDTO create(RoleRequestDTO dto) {
        Role newRole = roleMapper.toDomain(dto);
        newRole.setName(dto.name().toUpperCase());

        if(roleRepository.findByName(newRole.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Função já registrada.");
        }

        newRole = roleRepository.save(newRole);
        return roleMapper.toDTO(newRole);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Função não encontrada no registros."));
    }

    @Override
    public RoleResponseDTO findByNameToDTO(String name) {
        return roleMapper.toDTO(findByName(name));
    }

    @Override
    public Page<RoleResponseDTO> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable).map(roleMapper::toDTO);
    }

    @Transactional
    @Override
    public RoleResponseDTO update(UUID id, RoleRequestDTO dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Função não encontrada nos registros."));
        roleMapper.updateRoleFromDTO(dto, role);
        roleRepository.save(role);
        return roleMapper.toDTO(role);
    }

    @Transactional
    @Override
    public RoleResponseDTO deleteByName(String name) {
        Role role = roleRepository.findByName(name.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Função não encontrada nos registros."));
        roleRepository.delete(role);
        return roleMapper.toDTO(role);
    }
}
