package com.hby.coachfit.backend.dto;

import com.hby.coachfit.backend.domain.enums.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class WorkoutSessionDetailResponse {
    private Long sessionId;
    private LocalDate sessionDate;
    private SessionStatus status;

    private Long assignedProgramId;
    private Long programId;
    private String programName;

    private Integer dayIndex;
    private String dayName;

    private List<WorkoutExerciseDetailResponse> exercises;
}
