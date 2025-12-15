package com.hby.coachfit.backend.repository;

import com.hby.coachfit.backend.domain.AssignedProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignedProgramRepository extends JpaRepository<AssignedProgram, Long> {
    List<AssignedProgram> findByClientId(Long clientId);
}
