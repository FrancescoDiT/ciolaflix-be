package xyz.fdt.ciolaflixbe.exception.user;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class SignupException extends CiolaException {

    public SignupException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public SignupException(String message, Throwable cause) {
        super(message, HttpStatus.BAD_REQUEST, cause);
    }
}
