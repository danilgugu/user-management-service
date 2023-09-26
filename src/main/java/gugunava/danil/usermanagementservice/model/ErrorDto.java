package gugunava.danil.usermanagementservice.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ErrorDto {

    private final String message;

    private final Instant time;

    public ErrorDto(String message) {
        this.message = message;
        this.time = Instant.now();
    }
}
