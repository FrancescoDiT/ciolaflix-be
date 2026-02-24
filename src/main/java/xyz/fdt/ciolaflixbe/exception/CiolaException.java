package xyz.fdt.ciolaflixbe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CiolaException extends RuntimeException {

    private final HttpStatus status;

    protected CiolaException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    protected CiolaException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
