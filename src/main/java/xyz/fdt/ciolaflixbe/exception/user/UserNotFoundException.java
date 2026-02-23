package xyz.fdt.ciolaflixbe.exception.user;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class UserNotFoundException extends CiolaException {

    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
