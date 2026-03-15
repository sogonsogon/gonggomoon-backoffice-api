package com.sogonsogon.gonggomoonbackofficeapi.domain.user.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.user.dto.request.LoginRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.dto.response.TokenResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.entity.RefreshToken;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.infrastructure.RefreshTokenRepository;
import com.sogonsogon.gonggomoonbackofficeapi.global.security.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       TokenProvider tokenProvider,
                       RefreshTokenRepository refreshTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {

        log.info("Login Logic Start");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        log.info("First Authentication Success");

        TokenResponse tokenResponse = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = refreshTokenRepository.findByUserId(Long.valueOf(authentication.getName()))
                .map(existingToken -> {
                    return existingToken.updateValue(tokenResponse.refreshToken());
                })
                .orElseGet(() -> {
                    return RefreshToken.create(Long.valueOf(authentication.getName()), tokenResponse.refreshToken());
                });

        log.info("Refresh Token save logic end");

        refreshTokenRepository.save(refreshToken);

        return tokenResponse;
    }
}
