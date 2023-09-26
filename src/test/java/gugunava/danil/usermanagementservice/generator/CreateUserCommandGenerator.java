package gugunava.danil.usermanagementservice.generator;

import gugunava.danil.usermanagementservice.model.CreateUserCommand;

public class CreateUserCommandGenerator {

    public static CreateUserCommand valid() {
        return new CreateUserCommand(
                "Nick Fisher",
                "example@mail.com",
                "pass"
        );
    }

    public static CreateUserCommand invalid() {
        return new CreateUserCommand(
                " ",
                "not_email.com",
                " "
        );
    }
}
