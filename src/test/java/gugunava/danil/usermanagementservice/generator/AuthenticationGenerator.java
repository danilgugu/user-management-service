package gugunava.danil.usermanagementservice.generator;

import gugunava.danil.usermanagementservice.model.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthenticationGenerator {

    public static Authentication valid() {
        UserDetailsImpl admin = UserDetailsGenerator.admin();
        return UsernamePasswordAuthenticationToken.authenticated(admin, admin, admin.getAuthorities());
    }
}
