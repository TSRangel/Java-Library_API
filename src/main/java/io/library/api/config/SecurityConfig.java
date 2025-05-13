package io.library.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer -> configurer
                        .loginPage("/login"))
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/authors/**").hasRole("MANAGER");
                    authorize.requestMatchers(HttpMethod.DELETE, "/authors/**").hasRole("MANAGER");
                    authorize.requestMatchers(HttpMethod.PUT, "/authors/**").hasRole("MANAGER");
                    authorize.requestMatchers(HttpMethod.GET, "/authors/**").hasAnyRole("EMPLOYEE", "MANAGER");
                    authorize.requestMatchers(HttpMethod.POST, "/books/**").hasRole("MANAGER");
                    authorize.requestMatchers(HttpMethod.DELETE, "/books/**").hasRole("MANAGER");
                    authorize.requestMatchers(HttpMethod.PUT, "/books/**").hasRole("MANAGER");
                    authorize.requestMatchers(HttpMethod.GET, "/books/**").hasAnyRole("EMPLOYEE", "MANAGER");
                    authorize.requestMatchers(HttpMethod.POST, "/users/**").hasRole("MANAGER");
                    authorize.requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("MANAGER");
                    authorize.requestMatchers(HttpMethod.PUT, "/users/**").hasRole("MANAGER");
                    authorize.requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole("EMPLOYEE", "MANAGER");
                    authorize.requestMatchers(HttpMethod.POST, "/roles/**").hasRole("MANAGER");
                    authorize.requestMatchers(HttpMethod.DELETE, "/roles/**").hasRole("MANAGER");
                    authorize.requestMatchers(HttpMethod.PUT, "/roles/**").hasRole("MANAGER");
                    authorize.requestMatchers(HttpMethod.GET, "/roles/**").hasAnyRole("EMPLOYEE", "MANAGER");

                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails user1 = User.builder()
                .username("user")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("admin")
                .password(encoder.encode("123"))
                .roles("MANAGER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
