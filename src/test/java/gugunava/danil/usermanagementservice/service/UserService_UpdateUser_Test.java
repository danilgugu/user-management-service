package gugunava.danil.usermanagementservice.service;

import gugunava.danil.usermanagementservice.entity.UserEntity;
import gugunava.danil.usermanagementservice.exception.UserAlreadyExistsException;
import gugunava.danil.usermanagementservice.exception.UserNotFoundException;
import gugunava.danil.usermanagementservice.generator.UpdateUserCommandGenerator;
import gugunava.danil.usermanagementservice.model.UpdateUserCommand;
import gugunava.danil.usermanagementservice.model.User;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

public class UserService_UpdateUser_Test extends AbstractUserServiceTest {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/insert_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void whenUserIsRegistered_andEmailIsAvailable_thenReturnUpdatedUser() {
        UpdateUserCommand command = UpdateUserCommandGenerator.valid();
        long userId = -1L;

        User actual = userService.updateUser(userId, command);

        then(actual.getId()).isEqualTo(userId);
        then(actual.getUserName()).isEqualTo(command.getUserName());
        then(actual.getEmail()).isEqualTo(command.getEmail());
        UserEntity expected = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.byId(userId));
        then(encoder.matches(command.getPassword(), expected.getPassword())).isTrue();
    }

    @Test
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/insert_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void whenEmailIsUnavailable_thenThrowException() {
        UpdateUserCommand command = UpdateUserCommandGenerator.valid();
        long userId = -1L;

        ThrowableAssert.ThrowingCallable createUser = () -> userService.updateUser(userId, command);

        thenThrownBy(createUser)
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User with email 'updated@mail.com' already registered.");
    }

    @Test
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/insert_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void whenUpdateIsNotNeeded_thenReturnSameUser() {
        UpdateUserCommand command = UpdateUserCommandGenerator.updateNotNeeded();
        long userId = -1L;

        User actual = userService.updateUser(userId, command);

        then(actual.getId()).isEqualTo(userId);
        then(actual.getUserName()).isEqualTo(command.getUserName());
        then(actual.getEmail()).isEqualTo(command.getEmail());
        UserEntity expected = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.byId(userId));
        then(encoder.matches(command.getPassword(), expected.getPassword())).isTrue();
    }

    @Test
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/insert_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/delete_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void whenUpdateIsEmpty_thenReturnSameUser() {
        UpdateUserCommand command = UpdateUserCommandGenerator.empty();
        long userId = -1L;

        User actual = userService.updateUser(userId, command);

        UserEntity expected = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.byId(userId));
        then(actual.getId()).isEqualTo(expected.getId());
        then(actual.getUserName()).isEqualTo(expected.getUserName());
        then(actual.getEmail()).isEqualTo(expected.getEmail());
    }
}
