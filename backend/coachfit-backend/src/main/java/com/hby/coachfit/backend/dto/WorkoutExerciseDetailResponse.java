package com.hby.coachfit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class WorkoutExerciseDetailResponse {
    private Long programDayExerciseId;
    private Long exerciseId;
    private String exerciseName;
    private String primaryMuscle;

    // Template prévu
    private Integer orderIndex;
    private Integer sets;
    private Integer repsMin;
    private Integer repsMax;
    private BigDecimal targetRpeMin;
    private BigDecimal targetRpeMax;
    private Integer restSeconds;
    private String notes;

    // Ce qui a été réellement fait
    private List<LoggedSetItemResponse> loggedSets;
}
