package xyz.fdt.ciolaflixbe.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TmdbMovieDTO {
    private Long id;
    
    // Movie uses title, TV uses name
    private String title;
    private String name;
    
    private String overview;
    
    @JsonProperty("poster_path")
    private String posterPath;
    
    @JsonProperty("backdrop_path")
    private String backdropPath;
    
    @JsonProperty("vote_average")
    private Double voteAverage;
    
    @JsonProperty("release_date")
    private String releaseDate;
    
    @JsonProperty("first_air_date")
    private String firstAirDate;
    
    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
    
    @JsonProperty("media_type")
    private String mediaType; // Using String instead of Enum to handle variations robustly
}
