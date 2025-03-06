package xyz.tomorrowlearncamp.outsourcing.global.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.tomorrowlearncamp.outsourcing.global.entity.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Hidden
public class ExControllerAdvice {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> invalidRequestExHandler(InvalidRequestException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return getErrorResponse(status, ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedRequestException.class)
    public ResponseEntity<ErrorResponse> unauthorizedRequestExHandler(UnauthorizedRequestException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return getErrorResponse(status, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgsExHandler(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");
        return getErrorResponse(HttpStatus.valueOf(ex.getStatusCode().value()), errorMessage);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<ErrorResponse> servletRequestBindingExHandler(ServletRequestBindingException ex) {
        return getErrorResponse(HttpStatus.valueOf(ex.getStatusCode().value()), ex.getMessage());
    }


    public ResponseEntity<ErrorResponse> getErrorResponse(HttpStatus status, String message) {
        ErrorResponse errorResponse = new ErrorResponse(status.name(), status.value(), message);
        return new ResponseEntity<>(errorResponse, status);
    }
}
