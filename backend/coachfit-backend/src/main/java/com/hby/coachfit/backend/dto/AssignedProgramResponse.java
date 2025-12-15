package com.hby.coachfit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class AssignedProgramResponse {

    private Long assignedProgramId;
    private Long programId;
    private Long clientId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private int totalSessions;
}