package xyz.fdt.ciolaflixbe.exception.watchlater;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class MediaNotInWatchLaterException extends CiolaException {

    public MediaNotInWatchLaterException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
