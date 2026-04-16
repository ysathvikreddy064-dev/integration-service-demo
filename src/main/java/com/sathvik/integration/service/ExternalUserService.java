package com.sathvik.integration.service;

import com.sathvik.integration.model.ExternalUser;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ExternalUserService {

    private final WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com");

    @Retryable(maxAttempts = 3)
    public ExternalUser getUser(Long id) {
        return webClient.get()
                .uri("/users/" + id)
                .retrieve()
                .bodyToMono(ExternalUser.class)
                .block();
    }
}