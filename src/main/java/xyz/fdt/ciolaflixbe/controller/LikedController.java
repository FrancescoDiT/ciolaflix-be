package xyz.fdt.ciolaflixbe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fdt.ciolaflixbe.dto.LikedResponse;
import xyz.fdt.ciolaflixbe.service.LikedService;

@RestController
@RequestMapping("/liked")
@RequiredArgsConstructor
@Tag(name = "Liked", description = "Liked media management operations")
public class LikedController {

    private final LikedService likedService;

    @PostMapping("/add/{mediaId}")
    @Operation(
            summary = "Add media to liked",
            description = "Adds a media item to the current user's liked list. If the media doesn't exist in the database, it will be created automatically."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Media added successfully to liked list"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Media already liked by user",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<Void> addLiked(
            @Parameter(
                    description = "TMDB media identifier",
                    required = true,
                    example = "12345"
            )
            @PathVariable String mediaId
    ) {
        likedService.addLiked(mediaId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{mediaId}")
    @Operation(
            summary = "Remove media from liked",
            description = "Removes a media item from the current user's liked list"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Media removed successfully from liked list"
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
            )
    })
    public ResponseEntity<Void> deleteLiked(
            @Parameter(
                    description = "TMDB media identifier",
                    required = true,
                    example = "12345"
            )
            @PathVariable String mediaId
    ) {
        likedService.deleteLiked(mediaId);
        return ResponseEntity.ok().build();
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
            )
    })
    public ResponseEntity<LikedResponse> getLiked() {
        LikedResponse response = likedService.getLikedMediaIds();
        return ResponseEntity.ok(response);
    }
}
