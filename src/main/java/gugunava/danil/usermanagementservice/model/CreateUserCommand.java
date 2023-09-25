package gugunava.danil.usermanagementservice.model;

import lombok.Value;

@Value
public class CreateUserCommand {

  String userName;

  String email;
}

