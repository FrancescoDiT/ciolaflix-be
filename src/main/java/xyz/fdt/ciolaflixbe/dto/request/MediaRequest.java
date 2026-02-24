package xyz.fdt.ciolaflixbe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
@Schema(description = "Media action request")
public class MediaRequest {

    @Schema(
            description = "TMDB media identifier",
            example = "12345",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Media ID is required")
    private String mediaId;

    @Schema(
            description = "Media type (MOVIE or TV)",
            example = "MOVIE",
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"MOVIE", "TV"}
    )
    @NotBlank(message = "Media type is required")
    @Pattern(regexp = "^(?i)(MOVIE|TV)$", message = "Media type must be either MOVIE or TV")
    private String mediaType;

    private String timestamp;
}
