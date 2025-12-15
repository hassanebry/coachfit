package com.hby.coachfit.backend.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ApiErrorResponse {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private String traceId;
    private List<FieldErrorItem> fieldErrors;

    @Data
    @Builder
    @AllArgsConstructor
    public static class FieldErrorItem {
        private String field;
        private String message;
    }
}
