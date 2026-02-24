package xyz.fdt.ciolaflixbe.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fdt.ciolaflixbe.dto.request.MediaRequest;
import xyz.fdt.ciolaflixbe.service.WatchLaterService;

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
                    description = "Media already in watch later list or invalid request",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Media not found in TMDB",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error connecting to TMDB",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<Void> addWatchLater(@RequestBody @Valid MediaRequest request) {
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
                    description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Media not found in watch later list",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<Void> deleteWatchLater(@RequestBody @Valid MediaRequest request) {
        watchLaterService.deleteWatchLater(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
