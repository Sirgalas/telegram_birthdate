package ru.sergalas.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

public class SecurityBeans {
    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        return http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers("/login").permitAll()
                .anyRequest()
                .hasRole("admin")
        )
        .oauth2Client(Customizer.withDefaults())
        .oauth2Client(Customizer.withDefaults())
        .build();
    }
}
