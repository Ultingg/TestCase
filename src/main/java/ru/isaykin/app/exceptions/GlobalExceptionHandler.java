package ru.isaykin.app.exceptions;

import org.postgresql.util.PSQLException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        ApiError apiError = new ApiError(status, "Not valid value of field was entered.");
        apiError.addValidationErrors(exception.getBindingResult().getFieldErrors());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(value = IncorrectStatusException.class)
    public ResponseEntity<Object> handleIncorrectStatusException(IncorrectStatusException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", BAD_REQUEST.toString());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(
                body, BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(UserNotFoundException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", NOT_FOUND.toString());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(
                body, NOT_FOUND);
    }

    @ExceptionHandler(value = UserIsAlreadyExistException.class)
    public ResponseEntity<Object> handleIsAlreadyExistException(UserIsAlreadyExistException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", NOT_FOUND.toString());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(
                body, NOT_FOUND);
    }

    @ExceptionHandler(value = DbActionExecutionException.class)
    public ResponseEntity<Object> handleDuplicateException(DbActionExecutionException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        PSQLException psqlException = unwrapCause(PSQLException.class, exception);
        body.put("timestamp", new Date());
        body.put("status", BAD_REQUEST.toString());
        body.put("message", psqlException.getMessage());
        return new ResponseEntity<>(
                body, BAD_REQUEST);
    }

    public static <T> T unwrapCause(Class<T> clazz, Throwable e) {
        while (!clazz.isInstance(e) && e.getCause() != null && e != e.getCause()) {
            e = e.getCause();
        }
        return clazz.isInstance(e) ? clazz.cast(e) : null;
    }


}
