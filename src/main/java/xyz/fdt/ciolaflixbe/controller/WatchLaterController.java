package xyz.fdt.ciolaflixbe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fdt.ciolaflixbe.dto.request.MediaRequestDTO;
import xyz.fdt.ciolaflixbe.dto.response.MediaAndTypeDTO;
import xyz.fdt.ciolaflixbe.exception.ErrorResponse;
import xyz.fdt.ciolaflixbe.service.WatchLaterService;

import java.util.List;

@RestController
@RequestMapping("/watchlater")
@RequiredArgsConstructor
@Tag(name = "Watch Later", description = "Watch Later media management operations")
public class WatchLaterController {
    private final WatchLaterService watchLaterService;

    @PostMapping("/add")
    @Operation(
            summary = "Add media to watch later",
            description = "Adds a media item to the current user's watch later list."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Media added successfully to watch later list"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request, invalid media type, or media already in watch later list",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User or media not found in TMDB",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error connecting to TMDB",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<Void> addWatchLater(@RequestBody @Valid MediaRequestDTO request) {
        watchLaterService.addWatchLater(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete")
    @Operation(
            summary = "Remove media from watch later",
            description = "Removes a media item from the current user's watch later list."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Media removed successfully from watch later list"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request or invalid media type",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User, media, or watch later entry not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error connecting to TMDB",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<Void> deleteWatchLater(@RequestBody @Valid MediaRequestDTO request) {
        watchLaterService.deleteWatchLater(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/get")
    @Operation(
            summary = "Get watch later list",
            description = "Retrieves the list of media in the current user's watch later list."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Watch later list retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MediaAndTypeDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<List<MediaAndTypeDTO>> getWatchLater() {
        return ResponseEntity.ok(watchLaterService.getWatchLater());
    }
}
