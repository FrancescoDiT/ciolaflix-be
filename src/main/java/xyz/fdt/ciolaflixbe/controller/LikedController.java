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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fdt.ciolaflixbe.dto.LikedResponse;
import xyz.fdt.ciolaflixbe.dto.request.MediaRequest;
import xyz.fdt.ciolaflixbe.service.LikedService;

@RestController
@RequestMapping("/liked")
@RequiredArgsConstructor
@Tag(name = "Liked", description = "Liked media management operations")
public class LikedController {

    private final LikedService likedService;

    @PostMapping("/add")
    @Operation(
            summary = "Add media to liked",
            description = "Adds a media item to the current user's liked list. If the media doesn't exist in the database, it will be created automatically."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Media added successfully to liked list"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Media already liked by user or invalid request",
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
    public ResponseEntity<Void> addLiked(@RequestBody @Valid MediaRequest request) {
        likedService.addLiked(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete")
    @Operation(
            summary = "Remove media from liked",
            description = "Removes a media item from the current user's liked list"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Media removed successfully from liked list"
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
                    description = "Media not found in liked list",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error connecting to TMDB",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<Void> deleteLiked(@RequestBody @Valid MediaRequest request) {
        likedService.deleteLiked(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    @Operation(
            summary = "Get all liked media",
            description = "Retrieves a list of all media IDs that the current user has liked"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved liked media list",
                    content = @Content(schema = @Schema(implementation = LikedResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error connecting to TMDB",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<LikedResponse> getLiked() {
        LikedResponse response = likedService.getLikedMediaIds();
        return ResponseEntity.ok(response);
    }
}
