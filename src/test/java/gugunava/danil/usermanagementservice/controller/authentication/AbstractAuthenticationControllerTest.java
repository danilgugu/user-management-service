package gugunava.danil.usermanagementservice.controller.authentication;

import gugunava.danil.usermanagementservice.controller.AbstractControllerTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.jwt.JwtEncoder;

public abstract class AbstractAuthenticationControllerTest extends AbstractControllerTest {

    protected static final String BASE_URL = "/api/login/";

    @MockBean
    protected JwtEncoder jwtEncoder;

    @MockBean
    protected AuthenticationManager authenticationManager;
}
