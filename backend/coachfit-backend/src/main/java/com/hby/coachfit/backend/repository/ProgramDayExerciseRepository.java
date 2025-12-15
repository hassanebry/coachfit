package com.hby.coachfit.backend.repository;

import com.hby.coachfit.backend.domain.ProgramDayExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramDayExerciseRepository extends JpaRepository<ProgramDayExercise, Long> {
    List<ProgramDayExercise> findByProgramDayIdOrderByOrderIndexAsc(Long programDayId);
}
