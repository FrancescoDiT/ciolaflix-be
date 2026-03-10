package xyz.fdt.ciolaflixbe.model;

import xyz.fdt.ciolaflixbe.exception.media.InvalidMediaTypeException;

public enum MediaType {
    MOVIE, TV;

    public static MediaType fromString(String type) {
        try {
            return MediaType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidMediaTypeException("Invalid media type: " + type + ". Must be MOVIE or TV.");
        }
    }
}
