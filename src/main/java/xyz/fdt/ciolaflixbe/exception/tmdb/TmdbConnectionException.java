package xyz.fdt.ciolaflixbe.exception.tmdb;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class TmdbConnectionException extends CiolaException {

    public TmdbConnectionException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public TmdbConnectionException(String message, Throwable cause) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }
}
