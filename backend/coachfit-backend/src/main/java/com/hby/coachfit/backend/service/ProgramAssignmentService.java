package com.hby.coachfit.backend.service;

import com.hby.coachfit.backend.domain.*;
import com.hby.coachfit.backend.domain.enums.ProgramStatus;
import com.hby.coachfit.backend.domain.enums.SessionStatus;
import com.hby.coachfit.backend.domain.enums.UserRole;
import com.hby.coachfit.backend.dto.AssignProgramRequest;
import com.hby.coachfit.backend.dto.AssignedProgramResponse;
import com.hby.coachfit.backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProgramAssignmentService {

    private final ProgramRepository programRepository;
    private final UserRepo userRepository;
    private final AssignedProgramRepository assignedProgramRepository;
    private final ProgramDayRepository programDayRepository;
    private final WorkoutSessionRepository workoutSessionRepository;

    public ProgramAssignmentService(ProgramRepository programRepository, UserRepo userRepository, AssignedProgramRepository assignedProgramRepository, ProgramDayRepository programDayRepository, WorkoutSessionRepository workoutSessionRepository) {
        this.programRepository = programRepository;
        this.userRepository = userRepository;
        this.assignedProgramRepository = assignedProgramRepository;
        this.programDayRepository = programDayRepository;
        this.workoutSessionRepository = workoutSessionRepository;
    }

    @Transactional
    public AssignedProgramResponse assignProgram(AssignProgramRequest request) {
        // 1. Récupérer le programme
        Program program = programRepository.findById(request.getProgramId())
                .orElseThrow(() -> new IllegalArgumentException("Program not found with id " + request.getProgramId()));

        // 2. Récupérer le client
        User client = userRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found with id " + request.getClientId()));

        if (client.getRole() != UserRole.CLIENT) {
            throw new IllegalArgumentException("User " + client.getId() + " is not a client");
        }

        // 3. Récupérer les jours du programme
        List<ProgramDay> programDays = programDayRepository.findByProgramIdOrderByDayIndexAsc(program.getId());
        if (programDays.isEmpty()) {
            throw new IllegalArgumentException("Program " + program.getId() + " has no days");
        }

        // 4. Créer l'AssignedProgram
        LocalDate startDate = request.getStartDate();
        int weeks = (request.getNumberOfWeeks() == null || request.getNumberOfWeeks() <= 0)
                ? 4
                : request.getNumberOfWeeks();

        LocalDate endDate = startDate.plusWeeks(weeks).minusDays(1);
        AssignedProgram assignedProgram = AssignedProgram.builder()
                .program(program)
                .client(client)
                .startDate(startDate)
                .endDate(endDate)
                .status(ProgramStatus.ACTIVE)
                .build();
        assignedProgram = assignedProgramRepository.save(assignedProgram);

        // 5. Générer les séances (workout_sessions)
        int totalSessions = 0;

        for (int week = 0; week < weeks; week++) {
            for (ProgramDay programDay : programDays) {

                // On considère que dayIndex=1 -> startDate, 2 -> startDate+1, etc.
                LocalDate sessionDate = startDate
                        .plusWeeks(week)
                        .plusDays(programDay.getDayIndex() - 1L);

                WorkoutSession session = WorkoutSession.builder()
                        .assignedProgram(assignedProgram)
                        .programDay(programDay)
                        .sessionDate(sessionDate)
                        .status(SessionStatus.PLANNED)
                        .build();

                workoutSessionRepository.save(session);
                totalSessions++;
            }
        }

        // 6. Construire la réponse
        return AssignedProgramResponse.builder()
                .assignedProgramId(assignedProgram.getId())
                .programId(program.getId())
                .clientId(client.getId())
                .startDate(startDate)
                .endDate(endDate)
                .status(assignedProgram.getStatus().name())
                .totalSessions(totalSessions)
                .build();
    }
}
