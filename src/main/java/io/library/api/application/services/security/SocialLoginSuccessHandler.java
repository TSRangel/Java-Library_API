package io.library.api.application.services.security;

import io.library.api.application.services.RoleService;
import io.library.api.application.services.UserService;
import io.library.api.application.services.exceptions.ResourceNotFoundException;
import io.library.api.domain.entities.Role;
import io.library.api.domain.entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SocialLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        OAuth2User oath2User = (OAuth2User) authentication.getPrincipal();
        String email = oath2User.getAttribute("email");
        User user = null;
        Role baseRole = roleService.findByName("EMPLOYEE");

        try{
            user = userService.findByLogin(email);
        } catch (ResourceNotFoundException e) {
            if(email != null) {
                user = User.builder()
                        .login(email)
                        .password("123")
                        .roles(Set.of(baseRole))
                        .build();
                userService.register(user);
            }
        }

        authentication = new CustomAuthentication(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
