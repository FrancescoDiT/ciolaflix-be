package xyz.fdt.ciolaflixbe.exception.user;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class DuplicateEmailException extends CiolaException {

    public DuplicateEmailException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public DuplicateEmailException(String message, Throwable cause) {
        super(message, HttpStatus.CONFLICT, cause);
    }
}
