package com.hby.coachfit.backend.controller;


import com.hby.coachfit.backend.dto.AssignProgramRequest;
import com.hby.coachfit.backend.dto.AssignedProgramResponse;
import com.hby.coachfit.backend.service.ProgramAssignmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/program-assignments")
public class ProgramAssignmentController {

    private final ProgramAssignmentService assignmentService;

    public ProgramAssignmentController(ProgramAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public AssignedProgramResponse assignProgramToClient(@Valid @RequestBody AssignProgramRequest request) {
        return assignmentService.assignProgram(request);
    }
}
