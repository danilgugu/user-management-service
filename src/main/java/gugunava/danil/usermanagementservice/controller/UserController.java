package gugunava.danil.usermanagementservice.controller;

import gugunava.danil.usermanagementservice.model.CreateUserCommand;
import gugunava.danil.usermanagementservice.model.ErrorDto;
import gugunava.danil.usermanagementservice.model.UpdateUserCommand;
import gugunava.danil.usermanagementservice.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static gugunava.danil.usermanagementservice.config.SpringDocConfig.BEARER_AUTHENTICATION;

@RequestMapping("api/users")
public interface UserController {

    /**
     * GET /api/users : List of all users in the system
     *
     * @return successful operation (status code 200)
     */
    @Operation(
            operationId = "getUsers",
            summary = "List of all users in the system",
            tags = "user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    })
            }
    )
    @GetMapping
    ResponseEntity<List<User>> getUsers();

    /**
     * GET /api/users/{id} : Returns details of a specific user by their ID
     *
     * @param id Identifier of desired user (required)
     * @return successful operation (status code 200)
     * or invalid input (status code 400)
     * or user not found (status code 404)
     */
    @Operation(
            operationId = "getUser",
            summary = "Returns details of a specific user by their ID",
            tags = "user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "invalid input", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "user not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    })
            },
            security = @SecurityRequirement(name = BEARER_AUTHENTICATION)
    )
    @GetMapping("{id}")
    ResponseEntity<User> getUser(@Parameter(name = "id", description = "Identifier of desired user", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id);

    /**
     * POST /api/users : Create new user
     *
     * @param command Information about user to be created (optional)
     * @return successful operation (status code 200)
     */
    @Operation(
            operationId = "createUser",
            summary = "Create new user",
            tags = "user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    })
            },
            security = @SecurityRequirement(name = BEARER_AUTHENTICATION)
    )
    @PostMapping
    ResponseEntity<User> createUser(@Parameter(name = "CreateUserCommand", description = "Information about user to be created") @Valid @RequestBody CreateUserCommand command);

    /**
     * PUT /api/users/{id} : Updates the details of a specific user
     *
     * @param id      Identifier of desired user (required)
     * @param command Information about user to be updated (optional)
     * @return successful operation (status code 200)
     * or invalid input (status code 400)
     * or user not found (status code 404)
     */
    @Operation(
            operationId = "updateUser",
            summary = "Updates the details of a specific user",
            tags = "user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "invalid input", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "user not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    })
            },
            security = @SecurityRequirement(name = BEARER_AUTHENTICATION)
    )
    @PutMapping("{id}")
    ResponseEntity<User> updateUser(
            @Parameter(name = "id", description = "Identifier of desired user", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
            @Parameter(name = "UpdateUserCommand", description = "Information about user to be updated") @Valid @RequestBody UpdateUserCommand command
    );

    /**
     * DELETE /api/users/{id} : Removes a user from the system
     *
     * @param id Identifier of desired user (required)
     * @return successful operation (status code 200)
     * or user not found (status code 404)
     * or forbidden (status code 403)
     */
    @Operation(
            operationId = "deleteUser",
            summary = "Removes a user from the system",
            tags = "user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema())
                    }),
                    @ApiResponse(responseCode = "404", description = "user not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "forbidden", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    })
            },
            security = @SecurityRequirement(name = BEARER_AUTHENTICATION)
    )
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER.DELETE')")
    ResponseEntity<Void> deleteUser(@Parameter(name = "id", description = "Identifier of desired user", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id);
}
