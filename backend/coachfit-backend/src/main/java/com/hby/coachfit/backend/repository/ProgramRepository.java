package com.hby.coachfit.backend.repository;

import com.hby.coachfit.backend.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByCoachId(Long coachId);
}
