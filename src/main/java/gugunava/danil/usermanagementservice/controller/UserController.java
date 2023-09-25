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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/users")
public interface UserController {

    /**
     * GET /users : List of all users in the system
     *
     * @param limit (optional)
     * @return successful operation (status code 200)
     * or invalid limit (status code 400)
     */
    @Operation(
            operationId = "getUsers",
            summary = "List of all users in the system",
            tags = {"user"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "invalid limit", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    })
            }
    )
    @GetMapping
    ResponseEntity<List<User>> getUsers(@Parameter(name = "limit", description = "", in = ParameterIn.QUERY) @RequestParam(value = "limit", required = false) Integer limit);

    /**
     * GET /users/{id} : Returns details of a specific user by their ID
     *
     * @param id (required)
     * @return successful operation (status code 200)
     * or invalid input (status code 400)
     * or user not found (status code 404)
     */
    @Operation(
            operationId = "getUser",
            summary = "Returns details of a specific user by their ID",
            tags = {"user"},
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
            security = {
                    @SecurityRequirement(name = "user_auth")
            }
    )
    @GetMapping("{id}")
    ResponseEntity<User> getUser(@Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id);

    /**
     * POST /users : Create new user
     *
     * @param createUserCommand Information about user to be created (optional)
     * @return successful operation (status code 200)
     */
    @Operation(
            operationId = "createUser",
            summary = "Create new user",
            tags = {"user"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "user_auth")
            }
    )
    @PostMapping
    ResponseEntity<User> createUser(@Parameter(name = "CreateUserCommand", description = "Information about user to be created") @Valid @RequestBody CreateUserCommand createUserCommand);

    /**
     * PUT /users/{id} : Updates the details of a specific user
     *
     * @param id                (required)
     * @param updateUserCommand Information about user to be updated (optional)
     * @return successful operation (status code 200)
     * or invalid input (status code 400)
     * or user not found (status code 404)
     */
    @Operation(
            operationId = "updateUser",
            summary = "Updates the details of a specific user",
            tags = {"user"},
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
            security = {
                    @SecurityRequirement(name = "user_auth")
            }
    )
    @PutMapping("{id}")
    ResponseEntity<User> updateUser(
            @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
            @Parameter(name = "UpdateUserCommand", description = "Information about user to be updated") @Valid @RequestBody UpdateUserCommand updateUserCommand
    );

    /**
     * DELETE /users/{id} : Removes a user from the system
     *
     * @param id (required)
     * @return successful operation (status code 200)
     * or user not found (status code 404)
     */
    @Operation(
            operationId = "deleteUser",
            summary = "Removes a user from the system",
            tags = {"user"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "user not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "user_auth")
            }
    )
    @DeleteMapping("{id}")
    ResponseEntity<User> deleteUser(@Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id);
}
