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
import xyz.fdt.ciolaflixbe.dto.request.SignupRequest;
import xyz.fdt.ciolaflixbe.service.CiolaService;

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
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignupRequest request){
        ciolaService.signup(request);
        return ResponseEntity.ok().build();
    }

}
