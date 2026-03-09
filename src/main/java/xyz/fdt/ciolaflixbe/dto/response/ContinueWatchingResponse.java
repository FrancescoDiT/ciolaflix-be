package xyz.fdt.ciolaflixbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContinueWatchingResponse {
    private String mediaId;
    private String mediaType;
    private Integer seasonId;
    private Integer episodeId;
    private Integer currentTime;
}
