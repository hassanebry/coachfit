package com.hby.coachfit.backend.controller;

import com.hby.coachfit.backend.dto.MeResponse;
import com.hby.coachfit.backend.service.AuthService;
import com.hby.coachfit.backend.dto.AuthResponse;
import com.hby.coachfit.backend.dto.LoginRequest;
import com.hby.coachfit.backend.dto.RegisterRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public MeResponse me() {
        return authService.me();
    }
}