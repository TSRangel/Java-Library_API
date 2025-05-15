package io.library.api.application.services;

import io.library.api.adapter.DTOs.requests.UserRequestDTO;
import io.library.api.adapter.DTOs.responses.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

public interface UserService {
    UserResponseDTO register(UserRequestDTO dto);
    UserResponseDTO findByLogin(String login);
    Page<UserResponseDTO> findAll(Pageable pageable);
    void update(UserRequestDTO dto);
    void deleteByLogin(String login);
}
