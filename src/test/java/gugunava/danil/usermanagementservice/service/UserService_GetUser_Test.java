package gugunava.danil.usermanagementservice.service;

import gugunava.danil.usermanagementservice.exception.UserNotFoundException;
import gugunava.danil.usermanagementservice.model.User;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

public class UserService_GetUser_Test extends AbstractUserServiceTest {

    @Test
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void whenUserNotExists_thenThrowException() {
        ThrowableAssert.ThrowingCallable getUser = () -> userService.getUser(-1L);

        thenThrownBy(getUser)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id -1 not found.");
    }

    @Test
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/insert_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void whenUserExists_thenReturnUser() {
        User actual = userService.getUser(-1L);

        then(actual.getId()).isEqualTo(-1L);
        then(actual.getUserName()).isEqualTo("Nick Fisher");
        then(actual.getEmail()).isEqualTo("example@mail.com");
    }
}
