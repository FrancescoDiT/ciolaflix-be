package xyz.fdt.ciolaflixbe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.web.reactive.function.client.ExchangeStrategies;

@Configuration
public class TmdbClientConfig {

    @Value("${tmdb.api-url}")
    private String tmdbApiUrl;

    @Value("${tmdb.api-key}")
    private String tmdbApiKey;

    @Bean
    public WebClient tmdbWebClient() {
        // Increase buffer size to 10MB to handle large TMDB responses (e.g. TV seasons with many episodes/credits)
        final int size = 10 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .baseUrl(tmdbApiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + tmdbApiKey)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }
}
