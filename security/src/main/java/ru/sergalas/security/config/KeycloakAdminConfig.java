package ru.sergalas.security.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminConfig {
    @Value("${app.keycloak.admin.url}")
    private String keycloakAdminUrl;

    @Value("${app.keycloak.admin.realm}")
    private String masterRealm;

    @Value("${app.keycloak.admin.username}")
    private String username;

    @Value("${app.keycloak.admin.password}")
    private String password;

    @Value("${app.keycloak.admin.client-id}")
    private String clientId;

    @Bean
    public Keycloak keycloakAdmin() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakAdminUrl)
                .realm(masterRealm)
                .username(username)
                .password(password)
                .clientId(clientId)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }

}
