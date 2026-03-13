package com.sogonsogon.gonggomoonbackofficeapi.domain.user.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.user.application.AuthService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.dto.request.LoginRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.dto.response.TokenResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {

        log.info("Enter AuthController.login");
        TokenResponse tokenResponse = authService.login(request);

        return ResponseEntity.ok(tokenResponse);
    }
}
