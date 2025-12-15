package com.hby.coachfit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProgramDayResponse {
    private Long id;
    private Integer dayIndex;
    private String name;
    private List<ProgramDayExerciseResponse> exercises;
}
