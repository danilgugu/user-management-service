package gugunava.danil.usermanagementservice.service;

import gugunava.danil.usermanagementservice.exception.UserNotFoundException;
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

public class UserService_DeleteUser_Test extends AbstractUserServiceTest {

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
    void whenUserNotExists_thenThrowException() {
        ThrowableAssert.ThrowingCallable getUser = () -> userService.deleteUser(-1L);

        thenThrownBy(getUser)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id -1 not found.");
        verify(cache, never()).clear();
    }

    @Test
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/insert_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void whenUserExists_thenDeleteUser() {
        long userId = -1L;
        then(userRepository.existsById(userId)).isTrue();

        userService.deleteUser(userId);

        then(userRepository.existsById(userId)).isFalse();
        verify(cache).clear();
    }
}
