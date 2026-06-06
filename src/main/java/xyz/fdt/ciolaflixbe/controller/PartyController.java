package xyz.fdt.ciolaflixbe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fdt.ciolaflixbe.dto.request.EventRequest;
import xyz.fdt.ciolaflixbe.exception.ErrorResponse;
import xyz.fdt.ciolaflixbe.service.PartyService;

import java.util.Map;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
@Tag(name = "Party", description = "Watch party session management operations")
public class PartyController {

    private final PartyService partyService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    /**
     * REST endpoint to create a new party session
     * POST /api/party/session
     */
    @PostMapping("/session")
    @Operation(
        summary = "Create a new party session",
        description = "Creates a new watch party session and returns the generated session ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Party session created successfully",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - user not authenticated",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<String> createSession() {
        String sessionId = partyService.createSession();
        return ResponseEntity.ok(sessionId);
    }

    /**
     * WebSocket message endpoint to receive events
     * Client sends to: /app/party/event
     * Server broadcasts to: /topic/{sessionId}
     */
    @MessageMapping("/party")
    public void handleEvent(@Payload @Valid EventRequest request) {
        partyService.sendEvent(request);
    }
}
