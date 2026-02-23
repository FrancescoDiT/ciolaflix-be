package xyz.fdt.ciolaflixbe.exception.media;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class InvalidMediaTypeException extends CiolaException {

    public InvalidMediaTypeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
