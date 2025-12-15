package com.hby.coachfit.backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AssignProgramRequest {

    private Long programId;
    private Long clientId;
    private LocalDate startDate;
    private Integer numberOfWeeks;   // ex: 4
}
