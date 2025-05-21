package io.library.api.application.services.security;

import io.library.api.application.services.UserService;
import io.library.api.application.services.exceptions.ResourceNotFoundException;
import io.library.api.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoder encoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.findByLogin(authentication.getName());

        if(!encoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
            throw new ResourceNotFoundException("Usuário e/ou senha inválidos.");
        }

        return new CustomAuthentication(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
