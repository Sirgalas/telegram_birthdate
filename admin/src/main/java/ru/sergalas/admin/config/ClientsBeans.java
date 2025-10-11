package ru.sergalas.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.sergalas.admin.client.impl.DataClientImpl;
import ru.sergalas.admin.client.impl.UserClientImpl;
import ru.sergalas.admin.security.OAuthClientHttpRequestInterceptor;

@Configuration
public class ClientsBeans {




    @Value("${birthday.service.registrationId}")
    private String registrationId;

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder()
            .clientCredentials()
            .authorizationCode()
            .refreshToken()
            .build();

        DefaultOAuth2AuthorizedClientManager manager =
                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
        manager.setAuthorizedClientProvider(provider);

        return manager;
    }

    @Bean
    @Scope("prototype")
    public RestClient.Builder restClientBuilder(
        ClientRegistrationRepository clientRegistrationRepository,
        OAuth2AuthorizedClientRepository authorizedClientRepository
    ) {
        OAuth2AuthorizedClientManager manager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                authorizedClientRepository
        );

        OAuthClientHttpRequestInterceptor interceptor = new OAuthClientHttpRequestInterceptor(manager,registrationId);

        return RestClient.builder().requestInterceptor(interceptor);
    }

    @Bean
    DataClientImpl dataClientConfig(
        @Value("${birthday.service.data-service-uri}")String dataServiceUri,
        RestClient.Builder restClientBuilder
    ) {
        return new DataClientImpl(restClientBuilder.baseUrl(dataServiceUri).build());
    }

    @Bean
    UserClientImpl userClientConfig(
        @Value("${birthday.service.user-service-uri}") String userServiceUri,
        RestClient.Builder restClientBuilder
    ) {
        return new UserClientImpl(restClientBuilder.baseUrl(userServiceUri).build());
    }
}
