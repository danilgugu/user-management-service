package gugunava.danil.usermanagementservice.controller;

import gugunava.danil.usermanagementservice.exception.UserAlreadyExistsException;
import gugunava.danil.usermanagementservice.exception.UserNotFoundException;
import gugunava.danil.usermanagementservice.model.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDto handle(MethodArgumentNotValidException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorDto("Invalid request: " + e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorDto handle(UserNotFoundException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDto handle(UserAlreadyExistsException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorDto(e.getMessage());
    }
}
