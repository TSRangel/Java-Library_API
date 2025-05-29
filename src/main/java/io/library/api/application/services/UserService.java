package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.UserRequestDTO;
import io.library.api.adapter.DTOs.responses.UserResponseDTO;
import io.library.api.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

public interface UserService {
    User register(User user);
    UserResponseDTO registerToDTO(UserRequestDTO dto);
    User findByLogin(String login);
    UserResponseDTO findByLoginToDTO(String login);
    Page<UserResponseDTO> findAll(Pageable pageable);
    UserResponseDTO update(UserRequestDTO dto);
    UserResponseDTO deleteByLogin(String login);
}
