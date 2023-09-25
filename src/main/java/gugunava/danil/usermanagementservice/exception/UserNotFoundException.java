package gugunava.danil.usermanagementservice.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException byId(long id) {
        return new UserNotFoundException(String.format("User with id %d not found.", id));
    }
}
