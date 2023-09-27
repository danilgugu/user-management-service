package gugunava.danil.usermanagementservice.generator;

import gugunava.danil.usermanagementservice.model.UpdateUserCommand;

public class UpdateUserCommandGenerator {

    public static UpdateUserCommand valid() {
        return new UpdateUserCommand(
                "Will Tucker",
                "updated@mail.com",
                "new5pass!"
        );
    }

    public static UpdateUserCommand updateNotNeeded() {
        return new UpdateUserCommand(
                "Nick Fisher",
                "example@mail.com",
                "pass"
        );
    }

    public static UpdateUserCommand invalid() {
        return new UpdateUserCommand(
                "Bryan Willson",
                "not_email.com",
                "aear;obn"
        );
    }

    public static UpdateUserCommand empty() {
        return new UpdateUserCommand(
                "",
                "",
                " "
        );
    }
}
