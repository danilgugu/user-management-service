package gugunava.danil.usermanagementservice.controller;

import gugunava.danil.usermanagementservice.model.CreateUserCommand;
import gugunava.danil.usermanagementservice.model.UpdateUserCommand;
import gugunava.danil.usermanagementservice.model.User;
import gugunava.danil.usermanagementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<List<User>> getUsers(Integer limit) {
        return null;
    }

    @Override
    public ResponseEntity<User> getUser(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<User> createUser(CreateUserCommand createUserCommand) {
        return null;
    }

    @Override
    public ResponseEntity<User> updateUser(Long id, UpdateUserCommand updateUserCommand) {
        return null;
    }

    @Override
    public ResponseEntity<User> deleteUser(Long id) {
        return null;
    }
}
