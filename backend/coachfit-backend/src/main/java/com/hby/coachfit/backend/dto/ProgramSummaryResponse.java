package com.hby.coachfit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProgramSummaryResponse {
    private Long id;
    private String name;
    private String goal;
    private String level;
}
