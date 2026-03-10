package xyz.fdt.ciolaflixbe.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User information response")
public class UserInfoDTO {
    @Schema(description = "User first name", example = "John")
    private String name;

    @Schema(description = "User last name", example = "Doe")
    private String lastname;
}
