package xyz.fdt.ciolaflixbe.controller;

import org.springframework.web.bind.annotation.*;
import xyz.fdt.ciolaflixbe.service.TMDBProxyService;
import xyz.fdt.ciolaflixbe.dto.tmdb.TmdbResponseDTO;

@RestController
@RequestMapping("/tmdb")
@CrossOrigin(origins = "${cors.origin}")
public class TMDBProxyController {

    private final TMDBProxyService proxyService;

    public TMDBProxyController(TMDBProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @GetMapping("/trending/all/week")
    public TmdbResponseDTO getTrendingAll() {
        return proxyService.getTrendingAll();
    }

    @GetMapping("/trending/movie/week")
    public TmdbResponseDTO getTrendingMovies() {
        return proxyService.getTrendingMovies();
    }

    @GetMapping("/trending/tv/week")
    public TmdbResponseDTO getTrendingTV() {
        return proxyService.getTrendingTV();
    }

    @GetMapping("/movie/{id}")
    public Object getMovieDetail(
            @PathVariable Long id,
            @RequestParam(name = "append_to_response", required = false) String appendToResponse
    ) {
        return proxyService.getMovieDetail(id, appendToResponse);
    }

    @GetMapping("/tv/{id}")
    public Object getTvDetail(
            @PathVariable Long id,
            @RequestParam(name = "append_to_response", required = false) String appendToResponse
    ) {
        return proxyService.getTvDetail(id, appendToResponse);
    }
    
    @GetMapping("/tv/{id}/season/{seasonNumber}")
    public Object getSeasonEpisodes(
            @PathVariable Long id,
            @PathVariable Integer seasonNumber
    ) {
        return proxyService.getSeasonEpisodes(id, seasonNumber);
    }

    // Movies Lists
    @GetMapping("/movie/popular")
    public TmdbResponseDTO getPopularMovies() {
        return proxyService.getPopularMovies();
    }

    @GetMapping("/movie/top_rated")
    public TmdbResponseDTO getTopRatedMovies() {
        return proxyService.getTopRatedMovies();
    }

    @GetMapping("/movie/now_playing")
    public TmdbResponseDTO getNowPlayingMovies() {
        return proxyService.getNowPlayingMovies();
    }

    @GetMapping("/discover/movie")
    public TmdbResponseDTO getMoviesByGenre(@RequestParam(name = "with_genres") Integer genreId) {
        return proxyService.getMoviesByGenre(genreId);
    }

    // Search
    @GetMapping("/search/multi")
    public TmdbResponseDTO searchMulti(@RequestParam(name = "query") String query) {
        return proxyService.searchMulti(query);
    }

    // TV Lists
    @GetMapping("/tv/popular")
    public TmdbResponseDTO getPopularTV() {
        return proxyService.getPopularTV();
    }

    @GetMapping("/tv/top_rated")
    public TmdbResponseDTO getTopRatedTV() {
        return proxyService.getTopRatedTV();
    }

    @GetMapping("/tv/on_the_air")
    public TmdbResponseDTO getOnTheAirTV() {
        return proxyService.getOnTheAirTV();
    }

    @GetMapping("/discover/tv")
    public TmdbResponseDTO getTVByGenre(@RequestParam(name = "with_genres") Integer genreId) {
        return proxyService.getTVByGenre(genreId);
    }
}
