package xyz.fdt.ciolaflixbe.exception.watchlater;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class MediaAlreadyInWatchLaterException extends CiolaException {

    public MediaAlreadyInWatchLaterException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
