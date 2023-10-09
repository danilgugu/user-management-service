package gugunava.danil.usermanagementservice.controller.user;

import gugunava.danil.usermanagementservice.controller.AbstractControllerTest;
import gugunava.danil.usermanagementservice.repository.UserRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class AbstractUserControllerTest extends AbstractControllerTest {

    protected static final String BASE_URL = "/api/users/";

    @MockBean
    protected UserRepository userRepository;
}
