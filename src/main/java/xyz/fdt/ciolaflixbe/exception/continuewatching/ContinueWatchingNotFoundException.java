package xyz.fdt.ciolaflixbe.exception.continuewatching;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class ContinueWatchingNotFoundException extends CiolaException {

    public ContinueWatchingNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
