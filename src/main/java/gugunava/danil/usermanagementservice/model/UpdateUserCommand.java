package gugunava.danil.usermanagementservice.model;

import lombok.Value;

import javax.validation.constraints.Email;

@Value
public class UpdateUserCommand {

    String userName;

    @Email
    String email;
}

