package com.hby.coachfit.backend.service;

import com.hby.coachfit.backend.domain.enums.SessionStatus;
import com.hby.coachfit.backend.dto.*;
import com.hby.coachfit.backend.repository.*;
import com.hby.coachfit.backend.domain.*;
import com.hby.coachfit.backend.domain.enums.UserRole;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientWorkoutService {
    private final UserRepo userRepository;
    private final AssignedProgramRepository assignedProgramRepository;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final LoggedSetRepository loggedSetRepository;
    private final ProgramDayExerciseRepository programDayExerciseRepository;

    public ClientWorkoutService(UserRepo userRepository,
                                AssignedProgramRepository assignedProgramRepository,
                                WorkoutSessionRepository workoutSessionRepository, LoggedSetRepository loggedSetRepository, ProgramDayExerciseRepository programDayExerciseRepository) {
        this.userRepository = userRepository;
        this.assignedProgramRepository = assignedProgramRepository;
        this.workoutSessionRepository = workoutSessionRepository;
        this.loggedSetRepository = loggedSetRepository;
        this.programDayExerciseRepository = programDayExerciseRepository;
    }

    public List<WorkoutSessionSummaryResponse> getSessionsForClientOnDate(Long clientId, LocalDate date) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id " + clientId));

        if (client.getRole() != UserRole.CLIENT) {
            throw new IllegalArgumentException("User " + clientId + " is not a client");
        }

        // Récupérer tous les programmes assignés au client
        List<AssignedProgram> assignedPrograms = assignedProgramRepository.findByClientId(clientId);
        if (assignedPrograms.isEmpty()) {
            return List.of();
        }

        // Récupérer les sessions à la date donnée pour ces assigned_programs
        List<WorkoutSessionSummaryResponse> result = new ArrayList<>();

        for (AssignedProgram ap : assignedPrograms) {
            List<WorkoutSession> sessions = workoutSessionRepository
                    .findByAssignedProgramId(ap.getId());

            for (WorkoutSession session : sessions) {
                if (session.getSessionDate().equals(date)) {
                    ProgramDay programDay = session.getProgramDay();

                    WorkoutSessionSummaryResponse dto = WorkoutSessionSummaryResponse.builder()
                            .sessionId(session.getId())
                            .sessionDate(session.getSessionDate())
                            .status(session.getStatus())
                            .programId(ap.getProgram().getId())
                            .programName(ap.getProgram().getName())
                            .dayIndex(programDay.getDayIndex())
                            .dayName(programDay.getName())
                            .build();

                    result.add(dto);
                }
            }
        }

        return result;
    }

    @Transactional
    public LogWorkoutResponse logWorkout(LogWorkoutRequest request) {
        WorkoutSession session = workoutSessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id " + request.getSessionId()));

        int count = 0;

        if (request.getSets() != null) {
            for (LoggedSetRequest setReq : request.getSets()) {

                ProgramDayExercise pde = programDayExerciseRepository.findById(setReq.getProgramDayExerciseId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "ProgramDayExercise not found with id " + setReq.getProgramDayExerciseId()
                        ));

                LoggedSet ls = LoggedSet.builder()
                        .session(session)
                        .programDayExercise(pde)
                        .setIndex(setReq.getSetIndex())
                        .weightKg(setReq.getWeightKg())
                        .reps(setReq.getReps())
                        .rpe(setReq.getRpe())
                        .notes(setReq.getNotes())
                        .build();

                loggedSetRepository.save(ls);
                count++;
            }
        }

        session.setStatus(SessionStatus.DONE);
        workoutSessionRepository.save(session);

        return LogWorkoutResponse.builder()
                .sessionId(session.getId())
                .status(session.getStatus())
                .totalLoggedSets(count)
                .build();
    }

    public WorkoutSessionDetailResponse getSessionDetail(Long sessionId) {
        WorkoutSession session = workoutSessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id " + sessionId));

        AssignedProgram ap = session.getAssignedProgram();
        Program program = ap.getProgram();
        ProgramDay programDay = session.getProgramDay();

        // Récupérer tous les exos de ce jour de programme
        List<ProgramDayExercise> dayExercises =
                programDayExerciseRepository.findByProgramDayIdOrderByOrderIndexAsc(programDay.getId());

        // Récupérer tous les logged_sets pour cette session
        List<LoggedSet> loggedSets = loggedSetRepository.findBySessionId(session.getId());

        // Regrouper les logged_sets par programDayExerciseId
        Map<Long, List<LoggedSet>> logsByExercise = new HashMap<>();
        for (LoggedSet ls : loggedSets) {
            Long pdeId = ls.getProgramDayExercise().getId();
            logsByExercise.computeIfAbsent(pdeId, k -> new ArrayList<>()).add(ls);
        }

        // Construire les exercices de la réponse
        List<WorkoutExerciseDetailResponse> exerciseResponses = new ArrayList<>();

        for (ProgramDayExercise pde : dayExercises) {
            List<LoggedSet> setsForExercise = logsByExercise.getOrDefault(pde.getId(), List.of());
            List<LoggedSetItemResponse> loggedSetDtos = new ArrayList<>();

            for (LoggedSet ls : setsForExercise) {
                LoggedSetItemResponse lsDto = LoggedSetItemResponse.builder()
                        .id(ls.getId())
                        .setIndex(ls.getSetIndex())
                        .weightKg(ls.getWeightKg())
                        .reps(ls.getReps())
                        .rpe(ls.getRpe())
                        .notes(ls.getNotes())
                        .createdAt(ls.getCreatedAt())
                        .build();

                loggedSetDtos.add(lsDto);
            }

            Exercise exercise = pde.getExercise();

            WorkoutExerciseDetailResponse exDto = WorkoutExerciseDetailResponse.builder()
                    .programDayExerciseId(pde.getId())
                    .exerciseId(exercise.getId())
                    .exerciseName(exercise.getName())
                    .primaryMuscle(exercise.getPrimaryMuscle())
                    .orderIndex(pde.getOrderIndex())
                    .sets(pde.getSets())
                    .repsMin(pde.getRepsMin())
                    .repsMax(pde.getRepsMax())
                    .targetRpeMin(pde.getTargetRpeMin())
                    .targetRpeMax(pde.getTargetRpeMax())
                    .restSeconds(pde.getRestSeconds())
                    .notes(pde.getNotes())
                    .loggedSets(loggedSetDtos)
                    .build();

            exerciseResponses.add(exDto);
        }

        // Construire la réponse finale
        return WorkoutSessionDetailResponse.builder()
                .sessionId(session.getId())
                .sessionDate(session.getSessionDate())
                .status(session.getStatus())
                .assignedProgramId(ap.getId())
                .programId(program.getId())
                .programName(program.getName())
                .dayIndex(programDay.getDayIndex())
                .dayName(programDay.getName())
                .exercises(exerciseResponses)
                .build();
    }
}
