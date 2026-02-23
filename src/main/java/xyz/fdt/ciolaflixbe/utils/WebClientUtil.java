package xyz.fdt.ciolaflixbe.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import xyz.fdt.ciolaflixbe.model.MediaType;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClient tmdbWebClient;

    public void checkMediaExists(String mediaId, MediaType mediaType) {

        String endpoint = MediaType.MOVIE.equals(mediaType) ? "/movie/" : "/tv/";

        try {
            tmdbWebClient.get()
                    .uri(endpoint + mediaId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new NoSuchElementException("media not found in tmdb.", e);
        } catch (Exception e) {
            throw new RuntimeException("network problem on tmdb connection.", e);
        }
    }
}
