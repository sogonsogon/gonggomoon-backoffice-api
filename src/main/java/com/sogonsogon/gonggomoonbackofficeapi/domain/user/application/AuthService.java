package com.sogonsogon.gonggomoonbackofficeapi.domain.user.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.user.dto.request.LoginRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.dto.request.RefreshRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.dto.response.TokenResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.entity.RefreshToken;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.entity.User;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.error.UserErrorCode;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.infrastructure.RefreshTokenRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.infrastructure.UserRepository;
import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseException;
import com.sogonsogon.gonggomoonbackofficeapi.global.security.jwt.TokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       TokenProvider tokenProvider,
                       RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        TokenResponse tokenResponse = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = refreshTokenRepository.findByUserId(Long.valueOf(authentication.getName()))
                .map(existingToken -> {
                    return existingToken.updateValue(tokenResponse.refreshToken());
                })
                .orElseGet(() -> {
                    return RefreshToken.create(Long.valueOf(authentication.getName()), tokenResponse.refreshToken());
                });

        refreshTokenRepository.save(refreshToken);

        return tokenResponse;
    }

    @Transactional
    public TokenResponse refresh(RefreshRequest request, Long requestBy) {
        // 토큰 검증 로직
        tokenProvider.validateToken(request.refreshToken());

        Long userId = tokenProvider.getUserIdFromToken(request.refreshToken());

        if (!userId.equals(requestBy)) throw new BaseException(UserErrorCode.UNAUTHORIZED_ACCESS);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().name()) // Role이 Enum일 경우 .name()
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), "", authorities
        );

        // 유효한 세션이 아니거나 이미 로그아웃된 상태 -> 다시 로그인을 유도
        RefreshToken storedToken = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserErrorCode.LOGIN_REQUIRED));

        // RTR 검증 (토큰 탈취 의심 상황 -> DB에서 토큰 삭제 후 로그아웃 유도용 Exception)
        if (!storedToken.getToken().equals(request.refreshToken())) {
            refreshTokenRepository.delete(storedToken);
            throw new BaseException(UserErrorCode.INVALID_TOKEN);
        }

        TokenResponse response = tokenProvider.generateTokenDto(authentication);

        storedToken.updateValue(response.refreshToken());

        return response;
    }
}
