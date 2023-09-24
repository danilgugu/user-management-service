package gugunava.danil.usermanagementservice.api;

import gugunava.danil.usermanagementservice.model.CreateUserCommand;
import gugunava.danil.usermanagementservice.model.UpdateUserCommand;
import gugunava.danil.usermanagementservice.model.User;
import gugunava.danil.usermanagementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsersController implements UsersApiDelegate {

    private final UserService userService;

    @Override
    public ResponseEntity<User> createUser(CreateUserCommand createUserCommand) {
        return null;
    }

    @Override
    public ResponseEntity<User> deleteUser(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<User> getUser(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<User>> getUsers(Integer limit) {
        return null;
    }

    @Override
    public ResponseEntity<User> updateUser(Long id, UpdateUserCommand updateUserCommand) {
        return null;
    }
}
