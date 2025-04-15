package io.library.api.adapter.exceptionsHandlers.errors;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.HashMap;

public record FieldError(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss'Z'", timezone = "GMT")
        Instant timestamp,
        Integer status,
        String message,
        HashMap<String, String> errors
) {
    public FieldError(Integer status, String message, HashMap<String, String> errors) {
        this(Instant.now(), status, message, errors);
    }
}
