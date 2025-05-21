package io.library.api.application.services.security.utils;

import io.library.api.application.services.security.CustomAuthentication;
import io.library.api.domain.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextInfo {
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof CustomAuthentication customAuthentication) {
            return customAuthentication.getUser();
        }

        return null;
    }
}
