package com.hby.coachfit.backend.repository;

import com.hby.coachfit.backend.domain.LoggedSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoggedSetRepository extends JpaRepository<LoggedSet, Long> {
    List<LoggedSet> findBySessionId(Long sessionId);
}
