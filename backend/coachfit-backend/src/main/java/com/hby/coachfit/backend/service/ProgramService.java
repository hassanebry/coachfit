package com.hby.coachfit.backend.service;


import com.hby.coachfit.backend.domain.*;
import com.hby.coachfit.backend.domain.enums.UserRole;
import com.hby.coachfit.backend.dto.*;
import com.hby.coachfit.backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProgramService {
    private final UserRepo userRepo;
    private final ProgramRepository programRepository;
    private final ExerciseRepository exerciseRepository;
    private final ProgramDayRepository programDayRepository;
    private final ProgramDayExerciseRepository programDayExerciseRepository;

    public ProgramService(UserRepo userRepo, ProgramRepository programRepository, ExerciseRepository exerciseRepository, ProgramDayRepository programDayRepository, ProgramDayExerciseRepository programDayExerciseRepository) {
        this.userRepo = userRepo;
        this.programRepository = programRepository;
        this.exerciseRepository = exerciseRepository;
        this.programDayRepository = programDayRepository;
        this.programDayExerciseRepository = programDayExerciseRepository;
    }

    @Transactional
    public ProgramResponse createProgram(ProgramCreateRequest request) {
        // 1. Récupérer et vérifier le coach
        User coach = userRepo.findById(request.getCoachId())
                .orElseThrow(() -> new EntityNotFoundException("Coach not found with id " + request.getCoachId()));

        if (coach.getRole() != UserRole.COACH) {
            throw new IllegalArgumentException("User " + coach.getId() + " is not a coach");
        }

        // 2. Créer le Program
        Program program = Program.builder()
                .coach(coach)
                .name(request.getName())
                .goal(request.getGoal())
                .level(request.getLevel())
                .build();

        program = programRepository.save(program);

        // 3. Créer les jours et les exercices (sans streams)
        List<ProgramDayResponse> dayResponses = new ArrayList<>();

        if (request.getDays() != null) {
            for (ProgramDayRequest dayReq : request.getDays()) {

                // Création et sauvegarde du jour
                ProgramDay programDay = ProgramDay.builder()
                        .program(program)
                        .dayIndex(dayReq.getDayIndex())
                        .name(dayReq.getName())
                        .build();
                programDay = programDayRepository.save(programDay);

                List<ProgramDayExerciseResponse> exoResponses = new ArrayList<>();

                if (dayReq.getExercises() != null) {
                    for (ProgramDayExerciseRequest exReq : dayReq.getExercises()) {

                        // Vérifier que l'exercice existe
                        Exercise exercise = exerciseRepository.findById(exReq.getExerciseId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                        "Exercise not found with id " + exReq.getExerciseId()
                                ));

                        // Créer et sauvegarder le lien jour/exercice
                        ProgramDayExercise pde = ProgramDayExercise.builder()
                                .programDay(programDay)
                                .exercise(exercise)
                                .orderIndex(exReq.getOrderIndex())
                                .sets(exReq.getSets())
                                .repsMin(exReq.getRepsMin())
                                .repsMax(exReq.getRepsMax())
                                .targetRpeMin(exReq.getTargetRpeMin())
                                .targetRpeMax(exReq.getTargetRpeMax())
                                .restSeconds(exReq.getRestSeconds())
                                .notes(exReq.getNotes())
                                .build();

                        pde = programDayExerciseRepository.save(pde);

                        // Construire la réponse pour cet exercice du jour
                        ProgramDayExerciseResponse exoResp = ProgramDayExerciseResponse.builder()
                                .id(pde.getId())
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
                                .build();

                        exoResponses.add(exoResp);
                    }
                }

                // Construire la réponse pour le jour
                ProgramDayResponse dayResp = ProgramDayResponse.builder()
                        .id(programDay.getId())
                        .dayIndex(programDay.getDayIndex())
                        .name(programDay.getName())
                        .exercises(exoResponses)
                        .build();

                dayResponses.add(dayResp);
            }
        }

        // 4. Construire la réponse finale
        return ProgramResponse.builder()
                .id(program.getId())
                .coachId(coach.getId())
                .name(program.getName())
                .goal(program.getGoal())
                .level(program.getLevel())
                .days(dayResponses)
                .build();
    }

    public List<Program> getProgramsByCoach(Long coachId) {
        return programRepository.findByCoachId(coachId);
    }

    public List<ProgramSummaryResponse> getProgramSummariesByCoach(Long coachId) {
        List<Program> programs = programRepository.findByCoachId(coachId);

        List<ProgramSummaryResponse> result = new ArrayList<>();
        for (Program p : programs) {
            result.add(ProgramSummaryResponse.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .goal(p.getGoal())
                    .level(p.getLevel())
                    .build());
        }
        return result;
    }

    public ProgramResponse getProgramById(Long programId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new EntityNotFoundException("Program not found with id " + programId));

        List<ProgramDay> days = programDayRepository.findByProgramIdOrderByDayIndexAsc(programId);

        List<ProgramDayResponse> dayResponses = new ArrayList<>();

        for (ProgramDay day : days) {
            List<ProgramDayExercise> exos = programDayExerciseRepository.findByProgramDayIdOrderByOrderIndexAsc(day.getId());
            List<ProgramDayExerciseResponse> exoResponses = new ArrayList<>();

            for (ProgramDayExercise pde : exos) {
                Exercise ex = pde.getExercise();

                exoResponses.add(ProgramDayExerciseResponse.builder()
                        .id(pde.getId())
                        .exerciseId(ex.getId())
                        .exerciseName(ex.getName())
                        .primaryMuscle(ex.getPrimaryMuscle())
                        .orderIndex(pde.getOrderIndex())
                        .sets(pde.getSets())
                        .repsMin(pde.getRepsMin())
                        .repsMax(pde.getRepsMax())
                        .targetRpeMin(pde.getTargetRpeMin())
                        .targetRpeMax(pde.getTargetRpeMax())
                        .restSeconds(pde.getRestSeconds())
                        .notes(pde.getNotes())
                        .build());
            }

            dayResponses.add(ProgramDayResponse.builder()
                    .id(day.getId())
                    .dayIndex(day.getDayIndex())
                    .name(day.getName())
                    .exercises(exoResponses)
                    .build());
        }

        return ProgramResponse.builder()
                .id(program.getId())
                .coachId(program.getCoach().getId())
                .name(program.getName())
                .goal(program.getGoal())
                .level(program.getLevel())
                .days(dayResponses)
                .build();
    }
}
