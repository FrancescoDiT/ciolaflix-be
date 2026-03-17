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
import xyz.fdt.ciolaflixbe.service.LikedService;

import java.util.List;

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
                    description = "Invalid request, invalid media type, or media already liked by user",
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
    public ResponseEntity<Void> addLiked(@RequestBody @Valid MediaRequestDTO request) {
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
                    description = "User, media, or liked entry not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error connecting to TMDB",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<Void> deleteLiked(@RequestBody @Valid MediaRequestDTO request) {
        likedService.deleteLiked(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/get")
    @Operation(
            summary = "Get all liked media",
            description = "Retrieves a list of all media IDs that the current user has liked"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved liked media list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MediaAndTypeDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<List<MediaAndTypeDTO>> getLiked() {
        List<MediaAndTypeDTO> response = likedService.getLikedMediaIds();
        return ResponseEntity.ok(response);
    }
}
