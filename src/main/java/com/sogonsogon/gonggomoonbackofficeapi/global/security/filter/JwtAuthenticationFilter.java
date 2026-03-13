package com.sogonsogon.gonggomoonbackofficeapi.global.security.filter;

import com.sogonsogon.gonggomoonbackofficeapi.global.security.jwt.TokenProvider;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    public JwtAuthenticationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("method = " + request.getMethod());
        log.info("uri = " + request.getRequestURI());
        log.info("servletPath = " + request.getServletPath());

        try {
            String token = resolveToken(request);

            if (token == null || token.isBlank()) {
                filterChain.doFilter(request, response);
                return;
            }

            tokenProvider.validateToken(token);
            Authentication auth = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (JwtException e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }


    private String resolveToken(HttpServletRequest request) {

        String bearer = request.getHeader("Authorization");

        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) return bearer.substring(7);

        return null;
    }
}
