package com.hby.coachfit.backend.repository;

import com.hby.coachfit.backend.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByCoachId(Long coachId);
}
