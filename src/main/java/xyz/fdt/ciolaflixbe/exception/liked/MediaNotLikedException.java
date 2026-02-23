package xyz.fdt.ciolaflixbe.exception.liked;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class MediaNotLikedException extends CiolaException {

    public MediaNotLikedException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
