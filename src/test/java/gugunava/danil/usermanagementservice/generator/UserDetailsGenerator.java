package gugunava.danil.usermanagementservice.generator;

import gugunava.danil.usermanagementservice.model.UserDetailsImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class UserDetailsGenerator {

    public static UserDetailsImpl admin() {
        return new UserDetailsImpl(
                "admin@mail.com",
                "$2a$10$f..K.egFrctTsuDG8LMTQ.5NihkDVpvwtJLjNKvrI91IeUTek/LL2",
                List.of(new SimpleGrantedAuthority("USER.DELETE"))
        );
    }
}
