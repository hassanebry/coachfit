package com.hby.coachfit.backend.dto;

import com.hby.coachfit.backend.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MeResponse {
    private Long id;
    private String email;
    private String fullName;
    private UserRole role;
}
