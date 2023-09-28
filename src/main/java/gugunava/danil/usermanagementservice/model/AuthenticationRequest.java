package gugunava.danil.usermanagementservice.model;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class AuthenticationRequest {

    @Email
    @NotBlank
    String email;

    @NotBlank
    String password;
}
