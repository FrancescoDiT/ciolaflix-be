package xyz.fdt.ciolaflixbe.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import xyz.fdt.ciolaflixbe.dto.tmdb.TmdbResponseDTO;
import reactor.core.publisher.Mono;

@Service
public class TMDBProxyService {

    private final WebClient tmdbWebClient;

    public TMDBProxyService(WebClient tmdbWebClient) {
        this.tmdbWebClient = tmdbWebClient;
    }

    public TmdbResponseDTO getTrendingAll() {
        return tmdbWebClient.get()
                .uri("/trending/all/week")
                .retrieve()
                .bodyToMono(TmdbResponseDTO.class)
                .block();
    }

    public TmdbResponseDTO getTrendingMovies() {
        return tmdbWebClient.get()
                .uri("/trending/movie/week")
                .retrieve()
                .bodyToMono(TmdbResponseDTO.class)
                .block();
    }

    public TmdbResponseDTO getTrendingTV() {
        return tmdbWebClient.get()
                .uri("/trending/tv/week")
                .retrieve()
                .bodyToMono(TmdbResponseDTO.class)
                .block();
    }

    public Object getMovieDetail(Long id, String appendToResponse) {
        return tmdbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}")
                        .queryParam("append_to_response", appendToResponse != null ? appendToResponse : "credits,videos,images")
                        .build(id))
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object getTvDetail(Long id, String appendToResponse) {
        return tmdbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tv/{id}")
                        .queryParam("append_to_response", appendToResponse != null ? appendToResponse : "credits,videos,images,similar")
                        .build(id))
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
    
    public Object getTvSeason(Long id, Integer seasonNumber) {
        return tmdbWebClient.get()
                .uri("/tv/{id}/season/{seasonNumber}", id, seasonNumber)
                .retrieve()
                .bodyToMono(Object.class)
                .onErrorResume(e -> Mono.just(new Object())) // Fallback similar to frontend catch
                .block();
    }

    public Object getSeasonEpisodes(Long tvId, Integer seasonNumber) {
        return tmdbWebClient.get()
                .uri("/tv/{tvId}/season/{seasonNumber}", tvId, seasonNumber)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    // Movies Lists
    public TmdbResponseDTO getPopularMovies() {
        return tmdbWebClient.get()
                .uri("/movie/popular")
                .retrieve()
                .bodyToMono(TmdbResponseDTO.class)
                .block();
    }

    public TmdbResponseDTO getTopRatedMovies() {
        return tmdbWebClient.get()
                .uri("/movie/top_rated")
                .retrieve()
                .bodyToMono(TmdbResponseDTO.class)
                .block();
    }

    public TmdbResponseDTO getNowPlayingMovies() {
        return tmdbWebClient.get()
                .uri("/movie/now_playing")
                .retrieve()
                .bodyToMono(TmdbResponseDTO.class)
                .block();
    }

    public TmdbResponseDTO getMoviesByGenre(Integer genreId) {
        return tmdbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/discover/movie")
                        .queryParam("with_genres", genreId)
                        .build())
                .retrieve()
                .bodyToMono(TmdbResponseDTO.class)
                .block();
    }

    // Search
    public TmdbResponseDTO searchMulti(String query) {
        return tmdbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/multi")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToMono(TmdbResponseDTO.class)
                .block();
    }

    // TV Lists
    public TmdbResponseDTO getPopularTV() {
        return tmdbWebClient.get()
                .uri("/tv/popular")
                .retrieve()
                .bodyToMono(TmdbResponseDTO.class)
                .block();
    }

    public TmdbResponseDTO getTopRatedTV() {
        return tmdbWebClient.get()
                .uri("/tv/top_rated")
                .retrieve()
                .bodyToMono(TmdbResponseDTO.class)
                .block();
    }

    public TmdbResponseDTO getOnTheAirTV() {
        return tmdbWebClient.get()
                .uri("/tv/on_the_air")
                .retrieve()
                .bodyToMono(TmdbResponseDTO.class)
                .block();
    }

    public TmdbResponseDTO getTVByGenre(Integer genreId) {
        return tmdbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/discover/tv")
                        .queryParam("with_genres", genreId)
                        .build())
                .retrieve()
                .bodyToMono(TmdbResponseDTO.class)
                .block();
    }
}
