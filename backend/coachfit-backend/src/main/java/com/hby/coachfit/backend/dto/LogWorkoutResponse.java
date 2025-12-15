package com.hby.coachfit.backend.dto;

import com.hby.coachfit.backend.domain.enums.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LogWorkoutResponse {
    private Long sessionId;
    private SessionStatus status;
    private int totalLoggedSets;
}
