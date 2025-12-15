package com.hby.coachfit.backend.controller;


import com.hby.coachfit.backend.domain.Program;
import com.hby.coachfit.backend.dto.ProgramCreateRequest;
import com.hby.coachfit.backend.dto.ProgramResponse;
import com.hby.coachfit.backend.dto.ProgramSummaryResponse;
import com.hby.coachfit.backend.repository.ProgramRepository;
import com.hby.coachfit.backend.service.ProgramService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public ResponseEntity<ProgramResponse> createProgram(@Valid @RequestBody ProgramCreateRequest request) {
        ProgramResponse created = programService.createProgram(request);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    // GET /api/programs?coachId=1 : lister les programmes du coach
    @GetMapping
    public List<ProgramSummaryResponse> getProgramsByCoach(@RequestParam("coachId") Long coachId) {
        return programService.getProgramSummariesByCoach(coachId);
    }

    @GetMapping("/{programId}")
    public ProgramResponse getProgram(@PathVariable Long programId) {
        return programService.getProgramById(programId);
    }
}
