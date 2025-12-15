package com.hby.coachfit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class LoggedSetItemResponse {
    private Long id;
    private Integer setIndex;
    private BigDecimal weightKg;
    private Integer reps;
    private BigDecimal rpe;
    private String notes;
    private OffsetDateTime createdAt;
}
