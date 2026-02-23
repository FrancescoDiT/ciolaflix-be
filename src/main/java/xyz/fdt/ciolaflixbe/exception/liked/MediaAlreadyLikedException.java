package xyz.fdt.ciolaflixbe.exception.liked;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class MediaAlreadyLikedException extends CiolaException {

    public MediaAlreadyLikedException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public MediaAlreadyLikedException() {
        super("Media already liked by user.", HttpStatus.CONFLICT);
    }
}
