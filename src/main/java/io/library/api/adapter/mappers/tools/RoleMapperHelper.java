package io.library.api.adapter.mappers.tools;

import io.library.api.adapter.repositories.RoleRepository;
import io.library.api.application.services.exceptions.ResourceNotFoundException;
import io.library.api.domain.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleMapperHelper {
    private final RoleRepository roleRepository;

    public Set<Role> toRoles(Set<String> names) {
        return names.stream()
                .map(name -> roleRepository.findByName(name)
                    .orElseThrow(() -> new ResourceNotFoundException("Role não encontrada nos registros.")))
                .collect(Collectors.toSet());
    }
}
