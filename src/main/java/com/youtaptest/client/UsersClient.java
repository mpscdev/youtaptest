package com.youtaptest.client;

import com.youtaptest.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Component
public class UsersClient {

    private static final Logger logger = LoggerFactory.getLogger(UsersClient.class);
    private final WebClient client;

    public UsersClient(WebClient.Builder builder, @Value("${source.base.url}") String usersUrl) {
        logger.info("Source base URL: {}", usersUrl);
        this.client = builder.baseUrl(usersUrl).build();
    }

    public Mono<List<User>> getUsersBy(Optional<List<Long>> id, Optional<List<String>> username) {
        return this.client.get().uri(uriBuilder -> uriBuilder
                        .path("/users")
                        .queryParamIfPresent("id", id)
                        .queryParamIfPresent("username", username)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<User>>() {})
                .onErrorMap(RuntimeException::new);
    }
}
