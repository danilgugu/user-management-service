package gugunava.danil.usermanagementservice.service;

import gugunava.danil.usermanagementservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class AbstractUserServiceTest {

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserRepository userRepository;
}
