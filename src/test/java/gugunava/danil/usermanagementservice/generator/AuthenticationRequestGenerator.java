package gugunava.danil.usermanagementservice.generator;

import gugunava.danil.usermanagementservice.model.AuthenticationRequest;

public class AuthenticationRequestGenerator {

    public static AuthenticationRequest valid() {
        return new AuthenticationRequest(
                "admil@mail.com",
                "$2a$10$f..K.egFrctTsuDG8LMTQ.5NihkDVpvwtJLjNKvrI91IeUTek/LL2"
        );
    }

    public static AuthenticationRequest invalid() {
        return new AuthenticationRequest(
                "not-mail.com",
                " "
        );
    }
}
