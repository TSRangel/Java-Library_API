package io.library.api.adapter.mappers;

import io.library.api.adapter.DTOs.requests.UserRequestDTO;
import io.library.api.adapter.DTOs.responses.UserResponseDTO;
import io.library.api.adapter.mappers.tools.RoleMapperHelper;
import io.library.api.application.services.impl.UserServiceImpl;
import io.library.api.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {RoleMapper.class,
                RoleMapperHelper.class,
                UserServiceImpl.class,
        },
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toDomain(UserRequestDTO dto);
    UserResponseDTO toDTO(User user);
    void updateUserFromDTO(UserRequestDTO dto, @MappingTarget User user);
}
