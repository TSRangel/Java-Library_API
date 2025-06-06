package io.library.api.adapter.exceptionsHandlers;

import io.library.api.adapter.exceptionsHandlers.errors.FieldError;
import io.library.api.adapter.exceptionsHandlers.errors.StandartError;
import io.library.api.application.services.exceptions.ResourceAlreadyExistsException;
import io.library.api.application.services.exceptions.ResourceHasDependencies;
import io.library.api.application.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class ExceptionsControllerHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandartError>  standartError(RuntimeException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status).body(new StandartError(
                status.value(),
                "Algo inesperado aconteceu. Por favor contate o suporte.",
                request.getRequestURI()
        ));
    }

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
        HttpStatus status= HttpStatus.CONFLICT;

        return ResponseEntity.status(status)
                .body(new StandartError(
                    status.value(),
                    e.getMessage(),
                    request.getRequestURI()
                ));
    }

    @ExceptionHandler(ResourceHasDependencies.class)
    public ResponseEntity<StandartError> hasDependencies(ResourceHasDependencies e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        return ResponseEntity.status(status)
                .body(new StandartError(
                        status.value(),
                        e.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldError> fieldValidation(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HashMap<String, String> errors = new HashMap<>();

        e.getFieldErrors()
                .forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.status(status).body(new FieldError(
                status.value(),
                "Erro de validação de campos",
                errors
        ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandartError> notPermited(AccessDeniedException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        return ResponseEntity.status(status)
                .body(new StandartError(
                        status.value(),
                        "User does not have the necessary permission.",
                        request.getRequestURI()
                ));
    }
}
