package xyz.fdt.ciolaflixbe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TmdbClientConfig {

    @Value("${tmdb.api-url}")
    private String tmdbApiUrl;

    @Value("${tmdb.api-key}")
    private String tmdbApiKey;

    @Bean
    public WebClient tmdbWebClient() {
        return WebClient.builder()
                .baseUrl(tmdbApiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + tmdbApiKey)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }
}
