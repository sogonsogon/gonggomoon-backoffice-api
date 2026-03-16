package com.sogonsogon.gonggomoonbackofficeapi.domain.user.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.user.application.AuthService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.dto.request.LoginRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.dto.request.RefreshRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.dto.response.TokenResponse;
import com.sogonsogon.gonggomoonbackofficeapi.global.response.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {

        TokenResponse tokenResponse = authService.login(request);

        return ResponseEntity.ok(BaseResponse.success(tokenResponse));
    }

    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<TokenResponse>> refresh(@AuthenticationPrincipal UserDetails details,
                                                               @RequestBody @Valid RefreshRequest request) {

        return ResponseEntity.ok(BaseResponse.success(authService.refresh(request, Long.valueOf(details.getUsername()))));
    }
}
