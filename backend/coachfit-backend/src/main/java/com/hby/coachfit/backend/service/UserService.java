package com.hby.coachfit.backend.service;

import com.hby.coachfit.backend.domain.User;
import com.hby.coachfit.backend.repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void createUser(User user){
        userRepo.save(user);
    }
}
