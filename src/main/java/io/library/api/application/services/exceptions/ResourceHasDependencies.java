package io.library.api.application.services.exceptions;

public class ResourceHasDependencies extends RuntimeException {
    public ResourceHasDependencies(String message) {
        super(message);
    }
}
