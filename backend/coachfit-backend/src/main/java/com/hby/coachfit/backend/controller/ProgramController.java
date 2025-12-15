package com.hby.coachfit.backend.controller;


import com.hby.coachfit.backend.domain.Program;
import com.hby.coachfit.backend.dto.ProgramCreateRequest;
import com.hby.coachfit.backend.dto.ProgramResponse;
import com.hby.coachfit.backend.repository.ProgramRepository;
import com.hby.coachfit.backend.service.ProgramService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    // POST /api/programs : créer un programme complet
    @PostMapping
    public ProgramResponse createProgram(@RequestBody ProgramCreateRequest request) {
        return programService.createProgram(request);
    }

    // GET /api/programs?coachId=1 : lister les programmes du coach
    @GetMapping
    public List<Program> getProgramsByCoach(@RequestParam("coachId") Long coachId) {
        return programService.getProgramsByCoach(coachId);
    }
}
