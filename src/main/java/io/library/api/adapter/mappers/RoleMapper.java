package io.library.api.adapter.mappers;

import io.library.api.adapter.DTOs.requests.RoleRequestDTO;
import io.library.api.adapter.DTOs.responses.RoleResponseDTO;
import io.library.api.domain.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    RoleResponseDTO toDTO(Role role);
    Role toDomain(RoleRequestDTO dto);
    void updateRoleFromDTO(RoleRequestDTO dto, @MappingTarget Role role);

    default String map(Role role) {
        return role.getName();
    }

    default Role map(String roleName) {
        return Role.builder().name(roleName).build();
    }
}
