package com.api.kubernetes.common.util.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient basicWebClient() {
        return WebClient.builder().build();
    }
}
