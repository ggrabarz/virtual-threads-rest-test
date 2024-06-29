package com.example.restasync.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.HttpResources;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;

import java.time.Duration;

@Configuration
@EnableWebFlux
public class WebClientConfig {
    public static final Integer TIMEOUT_IN_MINUTES = 10; // long to see how many "blocking" I/O we can handle
    private static final Integer TIMEOUT_IN_SECONDS = TIMEOUT_IN_MINUTES * 60;
    private static final Integer TIMEOUT_IN_MILLIS = TIMEOUT_IN_SECONDS * 1000;

    /**
     * Configuration loosely based on the reference documentation
     * https://docs.spring.io/spring-boot/reference/io/rest-client.html#io.rest-client.webclient.customization
     * https://docs.spring.io/spring-framework/reference/web/webflux-webclient/client-builder.html#webflux-client-builder-reactor-resources
     */

    @Bean
    public WebClient webClient(HttpClient httpClient, ServerProperties serverProperties) {
        String baseUrl = "http://%s:%s".formatted(
                serverProperties.getAddress().getHostAddress(),
                serverProperties.getPort());

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public HttpClient httpClient(ConnectionProvider connectionProvider) {
        HttpResources.set(connectionProvider);
        return HttpClient.create(connectionProvider)
                .responseTimeout(Duration.ofMinutes(TIMEOUT_IN_MINUTES))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT_IN_MILLIS)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .runOn(LoopResources.create("hatetepe", 2, 16, true))
                .doOnConnected(connection -> connection
                        .addHandlerLast(new ReadTimeoutHandler(TIMEOUT_IN_SECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(TIMEOUT_IN_SECONDS))
                );
    }

    @Bean
    public ConnectionProvider connectionProvider() {
        return ConnectionProvider.builder("32k-provider")
                .maxConnections(32 * 1024)
                .pendingAcquireMaxCount(-1) // Allows unlimited pending acquire requests
                .build();
    }
}


