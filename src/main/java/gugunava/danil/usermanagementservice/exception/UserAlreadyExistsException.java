package gugunava.danil.usermanagementservice.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public static UserAlreadyExistsException withEmail(String email) {
        return new UserAlreadyExistsException("User with email '" + email + "' already registered.");
    }
}
