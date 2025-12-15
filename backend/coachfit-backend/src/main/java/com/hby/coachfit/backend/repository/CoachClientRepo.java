package com.hby.coachfit.backend.repository;

import com.hby.coachfit.backend.domain.CoachClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoachClientRepo extends JpaRepository<CoachClient, Long> {
    List<CoachClient> findByCoachId(Long coachId);
    List<CoachClient> findByClientId(Long clientId);
}
