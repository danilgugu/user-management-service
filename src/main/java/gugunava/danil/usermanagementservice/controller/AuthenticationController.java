package gugunava.danil.usermanagementservice.controller;

import gugunava.danil.usermanagementservice.model.AuthenticationRequest;
import gugunava.danil.usermanagementservice.model.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("api/login")
public interface AuthenticationController {

    /**
     * POST api/login : Login into system
     *
     * @param request Authentication information (email and password)
     * @return successful operation (status code 200)
     * or wrong password (status code 401)
     */
    @Operation(
            operationId = "login",
            summary = "Authenticate user",
            tags = {"user"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "wrong email or password", content = {
                            @Content(mediaType = "application/json", schema = @Schema())
                    })
            }
    )
    @PostMapping
    ResponseEntity<AuthenticationResponse> login(
            @Parameter(name = "AuthenticationRequest", description = "Authentication information (email and password)")
            @Valid @RequestBody AuthenticationRequest request
    );

}
