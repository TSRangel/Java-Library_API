package io.library.api.application.services.exceptions;

import java.util.NoSuchElementException;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
