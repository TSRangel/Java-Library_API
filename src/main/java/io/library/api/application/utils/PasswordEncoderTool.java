package io.library.api.application.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderTool {
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10);
    }
}
