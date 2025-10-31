package com.hr.companion.api.exception;

import com.hr.companion.api.util.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("Field Validation Error: {}", ex.getMessage());
        List<String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed for one or more fields",
                fieldErrors,
                request
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentials(
            BadCredentialsException ex, HttpServletRequest request) {
        log.error("Authentication failed: invalid credentials");
        return buildResponse(
                HttpStatus.UNAUTHORIZED,
                "Invalid username or password",
                null,
                request
        );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiErrorResponse> handleDisabledUser(DisabledException ex, HttpServletRequest request) {
        log.error("Authentication failed: user account is disabled");
        return buildResponse(
                HttpStatus.FORBIDDEN,
                "User account is disabled",
                null,
                request
        );
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ApiErrorResponse> handleLockedUser(LockedException ex, HttpServletRequest request) {
        log.error("Authentication failed: user account is locked");
        return buildResponse(
                HttpStatus.FORBIDDEN,
                "User account is locked",
                null,
                request
        );
    }

    @ExceptionHandler(AccountExpiredException.class)
    public ResponseEntity<ApiErrorResponse> handleAccountExpired(
            AccountExpiredException ex, HttpServletRequest request) {
        log.error("Authentication failed: account expired");
        return buildResponse(
                HttpStatus.FORBIDDEN,
                "User account has expired",
                null,
                request
        );
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ApiErrorResponse> handleCredentialsExpired(
            CredentialsExpiredException ex, HttpServletRequest request) {
        log.error("Authentication failed: credentials expired");
        return buildResponse(
                HttpStatus.FORBIDDEN,
                "User credentials have expired",
                null,
                request
        );
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthServiceException(
            AuthenticationServiceException ex, HttpServletRequest request) {
        log.error("Authentication service error: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal authentication service error",
                null,
                request
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleGenericAuthException(
            AuthenticationException ex, HttpServletRequest request) {
        log.error("Authentication failed: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.UNAUTHORIZED,
                "Authentication failed",
                null,
                request
        );
    }

    // Fallback for any other unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error occurred", ex);
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                null,
                request
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleUserExistsException(
            UserAlreadyExistsException ex, HttpServletRequest request) {
        log.error("User already exists: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "User already exists",
                null,
                request
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleUserDataViolationException(
            DataIntegrityViolationException ex, HttpServletRequest request) {
        log.error("There was a data integrity violation: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                null,
                request
        );
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleDepartmentNotFoundException(
            DepartmentNotFoundException ex, HttpServletRequest request) {
        log.error("Department not found: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Department not found",
                null,
                request
        );
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEmployeeNotFoundException(
            EmployeeNotFoundException ex, HttpServletRequest request) {
        log.error("Employee not found: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Employee not found",
                null,
                request
        );
    }

    @ExceptionHandler(EmergencyContactNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEmergencyContactNotFoundException(
            EmergencyContactNotFoundException ex, HttpServletRequest request) {
        log.error("Emergency contact not found: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Emergency contact not found",
                null,
                request
        );
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(
            HttpStatus status, String message, List<String> details, HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .details(details)
                .build();
        return ResponseEntity.status(status).body(response);
    }
}
