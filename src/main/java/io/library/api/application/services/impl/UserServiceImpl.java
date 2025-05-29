package io.library.api.application.services.impl;

import io.library.api.adapter.DTOs.requests.UserRequestDTO;
import io.library.api.adapter.DTOs.responses.UserResponseDTO;
import io.library.api.adapter.mappers.UserMapper;
import io.library.api.adapter.repositories.UserRepository;
import io.library.api.application.services.RoleService;
import io.library.api.application.services.UserService;
import io.library.api.application.services.exceptions.ResourceAlreadyExistsException;
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

    @Transactional
    @Override
    public UserResponseDTO registerToDTO(UserRequestDTO dto) {
        User newUser = userMapper.toDomain(dto);

        if(userRepository.findByLogin(newUser.getLogin()).isPresent()) {
            throw new ResourceAlreadyExistsException("Usuário já registrado.");
        }

        String password = newUser.getPassword();
        newUser.setPassword(encoder.encode(password));

        for(String role : dto.roles()) {
            Role searchedRole = roleService.findByName(role);
            newUser.addRole(searchedRole);
        }
        newUser = userRepository.save(newUser);

        return userMapper.toDTO(newUser);
    }

    @Transactional
    @Override
    public User register(User user) {
        if(userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new ResourceAlreadyExistsException("Usuário já registrado.");
        }

        String password = user.getPassword();
        user.setPassword(encoder.encode(password));

        return userRepository.save(user);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado nos registros."));
    }

    @Override
    public UserResponseDTO findByLoginToDTO(String login) {
        return userMapper.toDTO(findByLogin(login));
    }

    @Override
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDTO);
    }

    @Transactional
    @Override
    public UserResponseDTO update(UserRequestDTO dto) {
        User user = userRepository.findByLogin(dto.login())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado nos registros."));
        userMapper.updateUserFromDTO(dto, user);
        user.setPassword(encoder.encode(dto.password()));
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    @Override
    public UserResponseDTO deleteByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado nos registros."));
        userRepository.delete(user);
        return userMapper.toDTO(user);
    }
}
