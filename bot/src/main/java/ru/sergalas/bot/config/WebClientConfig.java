package ru.sergalas.bot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.sergalas.bot.data.TokenResponseData;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    @Value("${app.data.url}")
    private String url;

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    @Value("${spring.security.oauth2.client.registration.birthday-admin-client.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.birthday-admin-client.client-secret}")
    private String clientSecret;

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(url)
                .filter(tokenExchangeFilterFunction())
                .build();
    }

    private ExchangeFilterFunction tokenExchangeFilterFunction() {
        return (request, next) -> {
            return getAccessToken()
                    .flatMap(token -> {
                        ClientRequest filteredRequest = ClientRequest.from(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .build();
                        return next.exchange(filteredRequest);
                    });
        };
    }

    private Mono<String> getAccessToken() {
        String tokenUri = issuerUri + "/protocol/openid-connect/token";

        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder()
                .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        return WebClient.builder()
                .build()
                .post()
                .uri(tokenUri)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(TokenResponseData.class)
                .map(TokenResponseData::getAccessToken);
    }

}