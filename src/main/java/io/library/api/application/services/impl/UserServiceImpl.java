package io.library.api.application.services.impl;

import io.library.api.adapter.DTOs.requests.UserRequestDTO;
import io.library.api.adapter.DTOs.responses.UserResponseDTO;
import io.library.api.adapter.mappers.RoleMapper;
import io.library.api.adapter.mappers.UserMapper;
import io.library.api.adapter.repositories.UserRepository;
import io.library.api.application.services.RoleService;
import io.library.api.application.services.UserService;
import io.library.api.application.services.exceptions.ResourceNotFoundException;
import io.library.api.domain.entities.Role;
import io.library.api.domain.entities.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Transactional
    @Override
    public UserResponseDTO register(UserRequestDTO dto) {
        User newUser = userMapper.toDomain(dto);
        String password = newUser.getPassword();
        newUser.setPassword(encoder.encode(password));

        for(String role : dto.roles()) {
            Role searchedRole = roleMapper.toDomainFromResponseDTO(roleService.findByName(role));
            newUser.addRole(searchedRole);
        }
        userRepository.save(newUser);

        return userMapper.toDTO(newUser);
    }

    @Override
    public UserResponseDTO findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado nos registros."));
        return userMapper.toDTO(user);
    }

    @Override
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDTO);
    }

    @Transactional
    @Override
    public void update(UserRequestDTO dto) {
        User user = userRepository.findByLogin(dto.login())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado nos registros."));
        userMapper.updateUserFromDTO(dto, user);
        user.setPassword(encoder.encode(dto.password()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado nos registros."));
        userRepository.delete(user);
    }
}
