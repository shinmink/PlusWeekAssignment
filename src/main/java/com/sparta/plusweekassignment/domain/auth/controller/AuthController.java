package com.sparta.plusweekassignment.domain.auth.controller;

import com.sparta.plusweekassignment.domain.auth.service.AuthService;
import com.sparta.plusweekassignment.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j(topic = "AuthController")
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    // Refresh token
    @PostMapping("/auth/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        authService.refreshToken(request, response, userDetails.getUser());
    }
}
