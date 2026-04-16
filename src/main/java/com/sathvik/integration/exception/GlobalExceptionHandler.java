package com.sathvik.integration.exception;

import com.sathvik.integration.web.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExternalUserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ExternalUserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(ExternalServiceUnavailableException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnavailable(ExternalServiceUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure("Unexpected error occurred"));
    }
}
