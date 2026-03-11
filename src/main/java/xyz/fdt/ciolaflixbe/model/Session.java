package xyz.fdt.ciolaflixbe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class Session {
    private String sessionId;
    private Instant createdAt;
}
