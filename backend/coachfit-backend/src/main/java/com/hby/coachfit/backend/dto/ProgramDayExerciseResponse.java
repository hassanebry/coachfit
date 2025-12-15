package com.hby.coachfit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class ProgramDayExerciseResponse {
    private Long id;
    private Long exerciseId;
    private String exerciseName;
    private String primaryMuscle;

    private Integer orderIndex;
    private Integer sets;
    private Integer repsMin;
    private Integer repsMax;
    private BigDecimal targetRpeMin;
    private BigDecimal targetRpeMax;
    private Integer restSeconds;
    private String notes;
}
