package com.hby.coachfit.backend.controller;

import com.hby.coachfit.backend.dto.LogWorkoutRequest;
import com.hby.coachfit.backend.dto.LogWorkoutResponse;
import com.hby.coachfit.backend.dto.WorkoutSessionDetailResponse;
import com.hby.coachfit.backend.dto.WorkoutSessionSummaryResponse;
import com.hby.coachfit.backend.service.ClientWorkoutService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientWorkoutController {
    private final ClientWorkoutService clientWorkoutService;

    public ClientWorkoutController(ClientWorkoutService clientWorkoutService) {
        this.clientWorkoutService = clientWorkoutService;
    }

    // GET /api/client/sessions?clientId=2&date=2025-12-15
    @GetMapping("/sessions")
    public List<WorkoutSessionSummaryResponse> getSessionsForClientOnDate(
            @RequestParam("clientId") Long clientId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return clientWorkoutService.getSessionsForClientOnDate(clientId, date);
    }

    @PostMapping("/sessions/log")
    public LogWorkoutResponse logWorkout(@RequestBody LogWorkoutRequest request) {
        return clientWorkoutService.logWorkout(request);
    }

    // GET /api/client/sessions/{sessionId}/detail
    @GetMapping("/sessions/{sessionId}/detail")
    public WorkoutSessionDetailResponse getSessionDetail(@PathVariable("sessionId") Long sessionId) {
        return clientWorkoutService.getSessionDetail(sessionId);
    }
}
