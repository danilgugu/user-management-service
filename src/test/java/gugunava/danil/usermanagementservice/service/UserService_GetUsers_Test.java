package gugunava.danil.usermanagementservice.service;

import gugunava.danil.usermanagementservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

public class UserService_GetUsers_Test extends AbstractUserServiceTest {

    @Test
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void whenTableIsEmpty_thenReturnEmptyList() {
        List<User> actual = userService.getUsers();
        then(actual).isEmpty();
    }

    @Test
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/insert_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void whenTableIsNotEmpty_thenReturnListOfExistingUsers() {
        List<User> actual = userService.getUsers();

        then(actual).isNotEmpty();
        then(actual).hasSize(2);
        then(actual.get(0).getId()).isEqualTo(-1L);
        then(actual.get(0).getUserName()).isEqualTo("Nick Fisher");
        then(actual.get(0).getEmail()).isEqualTo("example@mail.com");
        then(actual.get(1).getId()).isEqualTo(-2L);
        then(actual.get(1).getUserName()).isEqualTo("Brad Greedy");
        then(actual.get(1).getEmail()).isEqualTo("updated@mail.com");
    }
}
