package com.sogonsogon.gonggomoonbackofficeapi.global.security.config;

import com.sogonsogon.gonggomoonbackofficeapi.global.security.filter.JwtAuthenticationFilter;
import com.sogonsogon.gonggomoonbackofficeapi.global.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                           CorsConfigurationSource corsConfigurationSource, AuthenticationEntryPoint authenticationEntryPoint) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {})
                .sessionManagement(session -> {session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);})
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/v1/admin/auth/login").permitAll()
                        .requestMatchers("/actuator/health").permitAll() // 헬스체크는 모두 허용
                        .requestMatchers("/api/v1/callbacks/**").permitAll() // AI 콜백은 모두 허용

                        .anyRequest().authenticated()
                )

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
