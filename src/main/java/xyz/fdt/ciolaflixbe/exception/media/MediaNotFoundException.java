package xyz.fdt.ciolaflixbe.exception.media;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class MediaNotFoundException extends CiolaException {

    public MediaNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
