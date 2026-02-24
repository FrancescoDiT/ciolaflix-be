package xyz.fdt.ciolaflixbe.exception.continuewatching;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class ContinueWatchingAlreadyExistsException extends CiolaException {

    public ContinueWatchingAlreadyExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
