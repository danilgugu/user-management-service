package gugunava.danil.usermanagementservice.service;

import gugunava.danil.usermanagementservice.entity.UserEntity;
import gugunava.danil.usermanagementservice.exception.UserAlreadyExistsException;
import gugunava.danil.usermanagementservice.exception.UserNotFoundException;
import gugunava.danil.usermanagementservice.model.CreateUserCommand;
import gugunava.danil.usermanagementservice.model.UpdateUserCommand;
import gugunava.danil.usermanagementservice.model.User;
import gugunava.danil.usermanagementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static gugunava.danil.usermanagementservice.util.StringUtil.isBlank;
import static gugunava.danil.usermanagementservice.util.StringUtil.isNotBlank;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConversionService conversionService;
    private final BCryptPasswordEncoder encoder;

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
        if (userRepository.existsByEmail(command.getEmail()))
            throw UserAlreadyExistsException.withEmail(command.getEmail());
        String password = encoder.encode(command.getPassword());
        UserEntity userEntity = UserEntity.createNew(command.getUserName(), command.getEmail(), password);
        UserEntity saved = userRepository.save(userEntity);
        return conversionService.convert(saved, User.class);
    }

    public User updateUser(Long id, UpdateUserCommand command) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.byId(id));
        if (isCommandEmpty(command))
            return conversionService.convert(userEntity, User.class);
        boolean updateNeeded = false;
        if (isNotBlank(command.getUserName()) && !command.getUserName().equals(userEntity.getUserName())) {
            userEntity.setUserName(command.getUserName());
            updateNeeded = true;
        }
        if (isNotBlank(command.getEmail()) && !command.getEmail().equals(userEntity.getEmail())) {
            if (userRepository.existsByEmail(command.getEmail()))
                throw UserAlreadyExistsException.withEmail(command.getEmail());
            userEntity.setEmail(command.getEmail());
            updateNeeded = true;
        }
        String updatedPassword = command.getPassword();
        if (isNotBlank(updatedPassword) && !encoder.matches(updatedPassword, userEntity.getPassword())) {
            userEntity.setPassword(encoder.encode(updatedPassword));
            updateNeeded = true;
        }
        if (updateNeeded)
            userEntity = userRepository.save(userEntity);
        return conversionService.convert(userEntity, User.class);
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
    }

    private boolean isCommandEmpty(UpdateUserCommand command) {
        return command == null || (isBlank(command.getUserName()) &&
                isBlank(command.getEmail()) &&
                isBlank(command.getPassword()));
    }
}
