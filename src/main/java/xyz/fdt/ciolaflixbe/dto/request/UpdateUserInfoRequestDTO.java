package xyz.fdt.ciolaflixbe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Update user information request")
public class UpdateUserInfoRequestDTO {
    @NotBlank(message = "Name is required")
    @Schema(description = "User first name", example = "John")
    private String name;

    @NotBlank(message = "Lastname is required")
    @Schema(description = "User last name", example = "Doe")
    private String lastname;
}
