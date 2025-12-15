package com.hby.coachfit.backend.repository;

import com.hby.coachfit.backend.domain.User;
import com.hby.coachfit.backend.domain.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRole(UserRole role);

}
