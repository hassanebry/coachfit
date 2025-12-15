package com.hby.coachfit.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProgramDayRequest {

    private Integer dayIndex;          // 1,2,3...
    private String name;               // "Upper body", "Lower body", etc.

    private List<ProgramDayExerciseRequest> exercises;
}
