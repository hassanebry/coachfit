package com.hby.coachfit.backend.dto;

import com.hby.coachfit.backend.domain.enums.UserRole;
import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private UserRole role;  // COACH ou CLIENT
}
