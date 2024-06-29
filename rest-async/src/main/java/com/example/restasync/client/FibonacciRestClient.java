package com.example.restasync.client;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@AllArgsConstructor
@Component
public class FibonacciRestClient {
    private static final String FIBO_PATH = "/fibo/{term}";
    private final WebClient webClient;

    @GetMapping(value = FIBO_PATH, produces = "application/json")
    public Mono<BigInteger> computeNthFibonacciTerm(@PathVariable Integer term) {
        return webClient
                .get()
                .uri(FIBO_PATH, term)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BigInteger.class);
    }
}
