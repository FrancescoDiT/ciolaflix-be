package xyz.fdt.ciolaflixbe.exception.tmdb;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class TmdbConnectionException extends CiolaException {

    public TmdbConnectionException(String message) {
        super(message, HttpStatus.BAD_GATEWAY);
    }

    public TmdbConnectionException(String message, Throwable cause) {
        super(message, HttpStatus.BAD_GATEWAY, cause);
    }
}
