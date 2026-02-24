package xyz.fdt.ciolaflixbe.exception.tmdb;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class TmdbMediaNotFoundException extends CiolaException {

    public TmdbMediaNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public TmdbMediaNotFoundException(String message, Throwable cause) {
        super(message, HttpStatus.NOT_FOUND, cause);
    }
}
