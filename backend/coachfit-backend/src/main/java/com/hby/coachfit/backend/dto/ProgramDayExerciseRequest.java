package com.hby.coachfit.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProgramDayExerciseRequest {

    private Long exerciseId;           // ID d'un exercice existant

    private Integer orderIndex;        // ordre dans la séance : 1,2,3...
    private Integer sets;              // nb de séries
    private Integer repsMin;           // 8
    private Integer repsMax;           // 12

    private BigDecimal targetRpeMin;   // ex: 7.0
    private BigDecimal targetRpeMax;   // ex: 8.0

    private Integer restSeconds;       // temps de récup en secondes
    private String notes;              // optionnel
}
