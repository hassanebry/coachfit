package com.hby.coachfit.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProgramCreateRequest {

    private Long coachId;          // ID du coach qui crée le programme
    private String name;
    private String goal;
    private String level;

    private List<ProgramDayRequest> days;
}
