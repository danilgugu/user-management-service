package gugunava.danil.usermanagementservice.model;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class CreateUserCommand {

  @NotBlank
  String userName;

  @Email
  @NotBlank
  String email;

  @NotBlank
  String password;
}

