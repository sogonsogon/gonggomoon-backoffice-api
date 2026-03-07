package com.sogonsogon.gonggomoonbackofficeapi.global.security.config;

import com.sogonsogon.gonggomoonbackofficeapi.global.security.filter.JwtAuthenticationFilter;
import com.sogonsogon.gonggomoonbackofficeapi.global.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
                                           CorsConfigurationSource corsConfigurationSource) throws Exception {
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceEncoding(true);

        httpSecurity

                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session -> {session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);})
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/v1/admin/auth/login").permitAll()
//                        .requestMatchers("/api/v1/admin/industries").permitAll()

                        .anyRequest().permitAll()
                )

//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint()
//                        .accessDeniedHandler()
//                )
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
//                .addFilterBefore();

        return httpSecurity.build();
    }
}
