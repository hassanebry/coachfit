package com.hby.coachfit.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoggedSetRequest {
    private Long programDayExerciseId;   // l'exercice du jour
    private Integer setIndex;            // série n°
    private BigDecimal weightKg;         // charge
    private Integer reps;                // reps
    private BigDecimal rpe;              // ressenti
    private String notes;
}
