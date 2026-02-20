package xyz.fdt.ciolaflixbe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "User's liked media response")
public class LikedResponse {

    @Schema(description = "List of media IDs that the user has liked")
    private final List<String> mediaIds;

    @Builder
    public LikedResponse(List<String> mediaIds) {
        this.mediaIds = mediaIds != null ? mediaIds : List.of();
    }
}
