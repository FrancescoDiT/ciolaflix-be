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
import xyz.fdt.ciolaflixbe.dto.request.ContinueWatchingRequestDTO;
import xyz.fdt.ciolaflixbe.dto.request.MediaRequestDTO;
import xyz.fdt.ciolaflixbe.dto.response.ContinueWatchingResponse;
import xyz.fdt.ciolaflixbe.service.ContinueWatchingService;

import java.util.List;

@RestController
@RequestMapping("/continuewatching")
@RequiredArgsConstructor
@Tag(name = "Continue Watching", description = "Continue watching media management operations")
public class ContinueWatchingController {

    private final ContinueWatchingService continueWatchingService;

    @PostMapping("/add")
    @Operation(
            summary = "Add or update continue watching",
            description = "Adds or updates a continue watching entry for the current user. If an entry with the same user and media exists, it will be updated with the new timestamp."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Continue watching entry added/updated successfully"
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
                    description = "Media not found in TMDB",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<Void> addContinueWatching(@RequestBody @Valid ContinueWatchingRequestDTO request) {
        continueWatchingService.addContinueWatching(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete")
    @Operation(
            summary = "Remove media from continue watching",
            description = "Removes a media item from the current user's continue watching list"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Media removed successfully from continue watching list"
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
                    description = "Media not found in continue watching list",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error connecting to TMDB",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<Void> deleteContinueWatching(@RequestBody @Valid MediaRequestDTO request) {
        continueWatchingService.deleteContinueWatching(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/get")
    @Operation(
            summary = "Get all continue watching media",
            description = "Retrieves a list of all media that the current user is watching"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved continue watching media list",
                    content = @Content(schema = @Schema(implementation = List.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<List<ContinueWatchingResponse>> getContinueWatching() {
        List<ContinueWatchingResponse> response = continueWatchingService.getContinueWatching();
        return ResponseEntity.ok(response);
    }
}
