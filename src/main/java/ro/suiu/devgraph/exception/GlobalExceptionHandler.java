package ro.suiu.devgraph.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the application.
 * Handles specific exceptions and maps them to appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        HttpStatusCode httpStatusCode = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = ErrorResponse.builder(e, httpStatusCode, "Resource Not Found").build();

        return new ResponseEntity<>(errorResponse, httpStatusCode);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e) {
        HttpStatusCode httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = ErrorResponse.builder(e, httpStatusCode, "Application Error").build();

        return new ResponseEntity<>(errorResponse, httpStatusCode);
    }

}
