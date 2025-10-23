package com.hr.companion.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Authentication failed: invalid credentials");
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleDisabledUser(DisabledException ex) {
        log.warn("Authentication failed: user account is disabled");
        return buildResponse(HttpStatus.FORBIDDEN, "User account is disabled");
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<?> handleLockedUser(LockedException ex) {
        log.warn("Authentication failed: user account is locked");
        return buildResponse(HttpStatus.FORBIDDEN, "User account is locked");
    }

    @ExceptionHandler(AccountExpiredException.class)
    public ResponseEntity<?> handleAccountExpired(AccountExpiredException ex) {
        log.warn("Authentication failed: account expired");
        return buildResponse(HttpStatus.FORBIDDEN, "User account has expired");
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<?> handleCredentialsExpired(CredentialsExpiredException ex) {
        log.warn("Authentication failed: credentials expired");
        return buildResponse(HttpStatus.FORBIDDEN, "User credentials have expired");
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<?> handleAuthServiceException(AuthenticationServiceException ex) {
        log.error("Authentication service error: {}", ex.getMessage());
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal authentication service error");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleGenericAuthException(AuthenticationException ex) {
        log.warn("Authentication failed: {}", ex.getMessage());
        return buildResponse(HttpStatus.UNAUTHORIZED, "Authentication failed");
    }

    // Fallback for any other unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserExistsException(UserAlreadyExistsException ex) {
        log.warn("User already exists: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "User already exists");
    }

    // Helper method for consistent response format
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "error", message,
                "timestamp", System.currentTimeMillis()
        ));
    }
}
