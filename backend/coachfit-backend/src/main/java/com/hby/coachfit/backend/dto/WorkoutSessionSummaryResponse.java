package com.hby.coachfit.backend.dto;

import com.hby.coachfit.backend.domain.enums.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class WorkoutSessionSummaryResponse {
    private Long sessionId;
    private LocalDate sessionDate;
    private SessionStatus status;

    private Long programId;
    private String programName;

    private Integer dayIndex;
    private String dayName;
}
