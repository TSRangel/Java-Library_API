package io.library.api.adapter.exceptionsHandlers;

import io.library.api.adapter.exceptionsHandlers.errors.StandartError;
import io.library.api.application.services.exceptions.ResourceAlreadyExistsException;
import io.library.api.application.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsControllerHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandartError> notFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status)
                .body(new StandartError(
                        status.value(),
                        e.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<StandartError> alreadyExists(ResourceAlreadyExistsException e, HttpServletRequest request) {
        HttpStatus status= HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(new StandartError(
                    status.value(),
                    e.getMessage(),
                    request.getRequestURI()
                ));
    }
}
