package xyz.fdt.ciolaflixbe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import xyz.fdt.ciolaflixbe.model.Event;

@Getter
public class EventRequest {
    @NotBlank(message = "Event type is required")
    private Event event;
    @NotBlank(message = "Session id is required")
    private String sessionId;
    @NotBlank(message = "Current timestamp is required")
    private String currentTimeStamp;
}
