package io.library.api.config;

import io.library.api.adapter.repositories.RoleRepository;
import io.library.api.adapter.repositories.UserRepository;
import io.library.api.domain.entities.Role;
import io.library.api.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SeedConfig implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        Role managerRole = roleRepository.findByName("MANAGER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("MANAGER").build()));
        Role employeeRole = roleRepository.findByName("EMPLOYEE")
                .orElseGet(() -> roleRepository.save(Role.builder().name("EMPLOYEE").build()));

        User manager = User.builder()
                .login("manager@library.com")
                .password(encoder.encode("manager"))
                .build();
        User employee = User.builder()
                .login("employee@library.com")
                .password(encoder.encode("employee"))
                .build();
        employee.addRole(employeeRole);
        manager.addRole(managerRole);

        if(userRepository.findByLogin(employee.getLogin()).isEmpty()) {
            userRepository.save(employee);
        }

        if(userRepository.findByLogin(manager.getLogin()).isEmpty()) {
            userRepository.save(manager);
        }
    }
}
