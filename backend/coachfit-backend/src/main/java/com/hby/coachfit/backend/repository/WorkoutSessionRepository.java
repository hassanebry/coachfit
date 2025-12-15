package com.hby.coachfit.backend.repository;

import com.hby.coachfit.backend.domain.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {
    List<WorkoutSession> findByAssignedProgramId(Long assignedProgramId);
    List<WorkoutSession> findBySessionDate(LocalDate date);
}
