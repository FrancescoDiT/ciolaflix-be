package xyz.fdt.ciolaflixbe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import xyz.fdt.ciolaflixbe.service.TMDBProxyService;
import xyz.fdt.ciolaflixbe.dto.tmdb.TmdbResponseDTO;

@RestController
@RequestMapping("/tmdb")
@Tag(name = "TMDB Proxy", description = "Proxy endpoints for TMDB API")
public class TMDBProxyController {

    private final TMDBProxyService proxyService;

    public TMDBProxyController(TMDBProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @GetMapping("/trending/all/week")
    @Operation(
        summary = "Get weekly trending media",
        description = "Retrieves all trending media (movies and TV shows) for the current week from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved trending media",
                    content = @Content(schema = @Schema(implementation = TmdbResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public TmdbResponseDTO getTrendingAll() {
        return proxyService.getTrendingAll();
    }

    @GetMapping("/trending/movie/week")
    @Operation(
        summary = "Get weekly trending movies",
        description = "Retrieves trending movies for the current week from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved trending movies",
                    content = @Content(schema = @Schema(implementation = TmdbResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public TmdbResponseDTO getTrendingMovies() {
        return proxyService.getTrendingMovies();
    }

    @GetMapping("/trending/tv/week")
    @Operation(
        summary = "Get weekly trending TV shows",
        description = "Retrieves trending TV shows for the current week from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved trending TV shows",
                    content = @Content(schema = @Schema(implementation = TmdbResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public TmdbResponseDTO getTrendingTV() {
        return proxyService.getTrendingTV();
    }

    @GetMapping("/movie/{id}")
    @Operation(
        summary = "Get movie details",
        description = "Retrieves detailed information about a specific movie from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved movie details"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public Object getMovieDetail(
            @Parameter(description = "TMDB movie ID") @PathVariable Long id,
            @Parameter(description = "Additional data to append (e.g. credits,videos,images)") @RequestParam(name = "append_to_response", required = false) String appendToResponse
    ) {
        return proxyService.getMovieDetail(id, appendToResponse);
    }

    @GetMapping("/tv/{id}")
    @Operation(
        summary = "Get TV show details",
        description = "Retrieves detailed information about a specific TV show from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved TV show details"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public Object getTvDetail(
            @Parameter(description = "TMDB TV show ID") @PathVariable Long id,
            @Parameter(description = "Additional data to append (e.g. credits,videos,images,similar)") @RequestParam(name = "append_to_response", required = false) String appendToResponse
    ) {
        return proxyService.getTvDetail(id, appendToResponse);
    }

    @GetMapping("/tv/{id}/season/{seasonNumber}")
    @Operation(
        summary = "Get season episodes",
        description = "Retrieves episode list for a specific season of a TV show from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved season episodes"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public Object getSeasonEpisodes(
            @Parameter(description = "TMDB TV show ID") @PathVariable Long id,
            @Parameter(description = "Season number") @PathVariable Integer seasonNumber
    ) {
        return proxyService.getSeasonEpisodes(id, seasonNumber);
    }

    // Movies Lists
    @GetMapping("/movie/popular")
    @Operation(
        summary = "Get popular movies",
        description = "Retrieves a list of popular movies from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved popular movies",
                    content = @Content(schema = @Schema(implementation = TmdbResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public TmdbResponseDTO getPopularMovies() {
        return proxyService.getPopularMovies();
    }

    @GetMapping("/movie/top_rated")
    @Operation(
        summary = "Get top rated movies",
        description = "Retrieves a list of top rated movies from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved top rated movies",
                    content = @Content(schema = @Schema(implementation = TmdbResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public TmdbResponseDTO getTopRatedMovies() {
        return proxyService.getTopRatedMovies();
    }

    @GetMapping("/movie/now_playing")
    @Operation(
        summary = "Get now playing movies",
        description = "Retrieves a list of movies currently playing in theaters from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved now playing movies",
                    content = @Content(schema = @Schema(implementation = TmdbResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public TmdbResponseDTO getNowPlayingMovies() {
        return proxyService.getNowPlayingMovies();
    }

    @GetMapping("/discover/movie")
    @Operation(
        summary = "Discover movies by genre",
        description = "Retrieves a list of movies filtered by genre ID from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved movies by genre",
                    content = @Content(schema = @Schema(implementation = TmdbResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public TmdbResponseDTO getMoviesByGenre(@Parameter(description = "TMDB genre ID") @RequestParam(name = "with_genres") Integer genreId) {
        return proxyService.getMoviesByGenre(genreId);
    }

    // Search
    @GetMapping("/search/multi")
    @Operation(
        summary = "Multi search",
        description = "Searches for movies, TV shows, and people matching the query from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved search results",
                    content = @Content(schema = @Schema(implementation = TmdbResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public TmdbResponseDTO searchMulti(@Parameter(description = "Search query string") @RequestParam(name = "query") String query) {
        return proxyService.searchMulti(query);
    }

    // TV Lists
    @GetMapping("/tv/popular")
    @Operation(
        summary = "Get popular TV shows",
        description = "Retrieves a list of popular TV shows from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved popular TV shows",
                    content = @Content(schema = @Schema(implementation = TmdbResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public TmdbResponseDTO getPopularTV() {
        return proxyService.getPopularTV();
    }

    @GetMapping("/tv/top_rated")
    @Operation(
        summary = "Get top rated TV shows",
        description = "Retrieves a list of top rated TV shows from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved top rated TV shows",
                    content = @Content(schema = @Schema(implementation = TmdbResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public TmdbResponseDTO getTopRatedTV() {
        return proxyService.getTopRatedTV();
    }

    @GetMapping("/tv/on_the_air")
    @Operation(
        summary = "Get on the air TV shows",
        description = "Retrieves a list of TV shows currently on the air from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved on the air TV shows",
                    content = @Content(schema = @Schema(implementation = TmdbResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public TmdbResponseDTO getOnTheAirTV() {
        return proxyService.getOnTheAirTV();
    }

    @GetMapping("/discover/tv")
    @Operation(
        summary = "Discover TV shows by genre",
        description = "Retrieves a list of TV shows filtered by genre ID from TMDB"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved TV shows by genre",
                    content = @Content(schema = @Schema(implementation = TmdbResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated"
            )
    })
    public TmdbResponseDTO getTVByGenre(@Parameter(description = "TMDB genre ID") @RequestParam(name = "with_genres") Integer genreId) {
        return proxyService.getTVByGenre(genreId);
    }
}
