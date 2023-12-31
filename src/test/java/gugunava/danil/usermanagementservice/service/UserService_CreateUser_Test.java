package gugunava.danil.usermanagementservice.service;

import gugunava.danil.usermanagementservice.exception.UserAlreadyExistsException;
import gugunava.danil.usermanagementservice.generator.CreateUserCommandGenerator;
import gugunava.danil.usermanagementservice.model.CreateUserCommand;
import gugunava.danil.usermanagementservice.model.User;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.jdbc.Sql;

import static gugunava.danil.usermanagementservice.config.CachingConfig.USERS;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserService_CreateUser_Test extends AbstractUserServiceTest {

    @MockBean
    private CacheManager cacheManager;

    @MockBean
    private Cache cache;

    @BeforeEach
    void setUp() {
        given(cacheManager.getCache(USERS)).willReturn(cache);
    }

    @Test
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void whenEmailIsAvailable_thenReturnCreatedUser() {
        CreateUserCommand command = CreateUserCommandGenerator.valid();

        User actual = userService.createUser(command);

        then(actual.getId()).isNotNull();
        then(actual.getUserName()).isEqualTo(command.getUserName());
        then(actual.getEmail()).isEqualTo(command.getEmail());
        verify(cache).clear();
    }

    @Test
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/insert_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void whenEmailIsUnavailable_thenThrowException() {
        CreateUserCommand command = CreateUserCommandGenerator.valid();

        ThrowableAssert.ThrowingCallable createUser = () -> userService.createUser(command);

        thenThrownBy(createUser)
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User with email 'example@mail.com' already registered.");
        verify(cache, never()).clear();
    }
}
