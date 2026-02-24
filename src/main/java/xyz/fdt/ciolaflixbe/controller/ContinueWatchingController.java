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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fdt.ciolaflixbe.dto.request.MediaRequest;
import xyz.fdt.ciolaflixbe.service.ContinueWatchingService;

@RestController
@RequestMapping("/continuewatching")
@RequiredArgsConstructor
@Tag(name = "Continue Watching", description = "Continue watching media management operations")
public class ContinueWatchingController {

    private final ContinueWatchingService continueWatchingService;

    @PutMapping
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
    public ResponseEntity<Void> addContinueWatching(@RequestBody @Valid MediaRequest request) {
        continueWatchingService.addContinueWatching(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
