package com.hr.companion.api.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiSuccessResponse<T> {
    private Instant timestamp;
    private int status;
    private boolean error;
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
