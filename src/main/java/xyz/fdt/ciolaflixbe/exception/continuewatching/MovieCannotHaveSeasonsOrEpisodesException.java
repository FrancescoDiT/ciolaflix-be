package xyz.fdt.ciolaflixbe.exception.continuewatching;

import org.springframework.http.HttpStatus;
import xyz.fdt.ciolaflixbe.exception.CiolaException;

public class MovieCannotHaveSeasonsOrEpisodesException extends CiolaException {
    public MovieCannotHaveSeasonsOrEpisodesException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
