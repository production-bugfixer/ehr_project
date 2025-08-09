package com.ehr.authenticate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    new AntPathRequestMatcher("/auth/**"),
                    new AntPathRequestMatcher("/forgot-password/**"),
                    new AntPathRequestMatcher("/authorize/**"),
                    new AntPathRequestMatcher("/authenticate/**"),
                    new AntPathRequestMatcher("/**", "OPTIONS")
                ).permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic().disable()
            .formLogin().disable()
            .logout().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    // Remove this entire bean to disable CORS handling in the auth service
    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() { ... }
}
