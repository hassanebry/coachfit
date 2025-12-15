package com.hby.coachfit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class LogWorkoutRequest {
    private Long sessionId;
    private List<LoggedSetRequest> sets;
}
