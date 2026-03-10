package xyz.fdt.ciolaflixbe.controller;

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
import xyz.fdt.ciolaflixbe.service.PartyService;

import java.util.Map;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    /**
     * REST endpoint to create a new party session
     * POST /api/party/session
     */
    @PostMapping("/session")
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
