package com.hr.companion.api.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Success response returned by the API")
public class ApiSuccessResponse<T> {
    @Schema(description = "Timestamp of the response", example = "2025-10-25 21:45:13.123+00")
    private Instant timestamp;
    @Schema(description = "HTTP status code", example = "200")
    private int status;
    @Schema(description = "Presence of an error", example = "false")
    private boolean error;
    @Schema(description = "User-friendly message", example = "Successfully retrieved data")
    private String message;
    private T data;

    public static <T> ApiSuccessResponse<T> success(T data, String message, int status) {
        return ApiSuccessResponse.<T>builder()
                .timestamp(Instant.now())
                .status(status)
                .error(false)
                .message(message)
                .data(data)
                .build();
    }
}
