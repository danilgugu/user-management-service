package gugunava.danil.usermanagementservice.controller;

import gugunava.danil.usermanagementservice.model.CreateUserCommand;
import gugunava.danil.usermanagementservice.model.UpdateUserCommand;
import gugunava.danil.usermanagementservice.model.User;
import gugunava.danil.usermanagementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<User> getUser(Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<User> createUser(CreateUserCommand command) {
        User user = userService.createUser(command);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<User> updateUser(Long id, UpdateUserCommand command) {
        User user = userService.updateUser(id, command);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
