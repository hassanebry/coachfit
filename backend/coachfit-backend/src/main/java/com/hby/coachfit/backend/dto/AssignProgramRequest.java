package com.hby.coachfit.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssignProgramRequest {


    @NotNull
    private Long programId;

    @NotNull
    private Long clientId;

    @NotNull
    private LocalDate startDate;

    @Min(1)
    private Integer numberOfWeeks;
}
