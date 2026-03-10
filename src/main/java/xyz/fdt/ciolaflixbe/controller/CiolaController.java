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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fdt.ciolaflixbe.dto.request.SignupRequestDTO;
import xyz.fdt.ciolaflixbe.dto.request.UpdateUserInfoRequestDTO;
import xyz.fdt.ciolaflixbe.dto.response.UserInfoDTO;
import xyz.fdt.ciolaflixbe.service.CiolaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/ciola")
@RequiredArgsConstructor
@Tag(name = "Ciola", description = "User management operations")
public class CiolaController {

    private final CiolaService ciolaService;

    @PostMapping("/signup")
    @Operation(
        summary = "User registration",
        description = "Register a new user with email, password, name and lastname"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User successfully registered"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = String.class))
        )
    })
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignupRequestDTO request){
        ciolaService.signup(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    @Operation(
        summary = "Get user info",
        description = "Retrieves the first and last name of the current authenticated user"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User info successfully retrieved",
            content = @Content(schema = @Schema(implementation = UserInfoDTO.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - user not authenticated"
        )
    })
    public ResponseEntity<UserInfoDTO> getUserInfo() {
        return ResponseEntity.ok(ciolaService.getUserInfo());
    }

    @PutMapping("/info")
    @Operation(
        summary = "Update user info",
        description = "Updates the first and last name of the current authenticated user"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User info successfully updated"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request data"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - user not authenticated"
        )
    })
    public ResponseEntity<Void> updateUserInfo(@RequestBody @Valid UpdateUserInfoRequestDTO request) {
        ciolaService.updateUserInfo(request);
        return ResponseEntity.ok().build();
    }

}
