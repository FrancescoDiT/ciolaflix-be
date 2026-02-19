package xyz.fdt.ciolaflixbe.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "User registration request")
public class SignupRequest {
    @Schema(description = "User email address", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Schema(description = "User password (min 8 characters)", example = "MySecurePass123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Schema(description = "Password confirmation", example = "MySecurePass123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Repeat password is required")
    private String repeatPassword;

    @Schema(description = "User first name", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "User last name", example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Lastname is required")
    private String lastname;

    @AssertTrue(message = "Passwords must match")
    @JsonIgnore
    public boolean isPasswordMatching() {
        return password != null && password.equals(repeatPassword);
    }
}
