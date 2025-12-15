package com.hby.coachfit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProgramResponse {
    private Long id;
    private Long coachId;
    private String name;
    private String goal;
    private String level;
    private List<ProgramDayResponse> days;
}
