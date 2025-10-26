package com.hr.companion.api.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Schema(description = "Error response returned by the API")
public class ApiErrorResponse {
    @Schema(description = "Timestamp of the response", example = "2025-10-25 21:45:13.123+00")
    private LocalDateTime timestamp;
    @Schema(description = "HTTP status code", example = "400")
    private int status;
    @Schema(description = "Error type or code", example = "BAD_REQUEST")
    private String error;
    @Schema(description = "User-friendly message", example = "Invalid input data")
    private String message;
    @Schema(description = "Optional path of the endpoint", example = "/api/v1/users")
    private String path;
    private List<String> details;
}
