package gugunava.danil.usermanagementservice.service;

import gugunava.danil.usermanagementservice.entity.UserEntity;
import gugunava.danil.usermanagementservice.exception.UserNotFoundException;
import gugunava.danil.usermanagementservice.model.CreateUserCommand;
import gugunava.danil.usermanagementservice.model.UpdateUserCommand;
import gugunava.danil.usermanagementservice.model.User;
import gugunava.danil.usermanagementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConversionService conversionService;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userEntity -> conversionService.convert(userEntity, User.class))
                .collect(Collectors.toList());
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .map(userEntity -> conversionService.convert(userEntity, User.class))
                .orElseThrow(() -> UserNotFoundException.byId(id));
    }

    public User createUser(CreateUserCommand command) {
        String password = passwordEncoder.encode(command.getPassword());
        UserEntity userEntity = UserEntity.createNew(command.getUserName(), command.getEmail(), password);
        UserEntity saved = userRepository.save(userEntity);
        return conversionService.convert(saved, User.class);
    }

    @Transactional
    public User updateUser(Long id, UpdateUserCommand command) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.byId(id));
        String updatedUserName = command.getUserName();
        if (updatedUserName != null && !updatedUserName.isBlank())
            userEntity.setUserName(updatedUserName);
        String updatedEmail = command.getEmail();
        if (updatedEmail != null && !updatedEmail.isBlank())
            userEntity.setEmail(updatedEmail);
        String updatedPassword = command.getPassword();
        if (updatedPassword != null && !updatedPassword.isBlank())
            userEntity.setPassword(passwordEncoder.encode(updatedPassword));
        UserEntity updated = userRepository.save(userEntity);
        return conversionService.convert(updated, User.class);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
