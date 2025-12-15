package com.hby.coachfit.backend.repository;

import com.hby.coachfit.backend.domain.ProgramDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramDayRepository extends JpaRepository<ProgramDay, Long> {
    List<ProgramDay> findByProgramIdOrderByDayIndexAsc(Long programId);
}
