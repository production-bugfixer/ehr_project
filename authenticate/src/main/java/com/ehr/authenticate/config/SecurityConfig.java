package com.ehr.authenticate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    new AntPathRequestMatcher("/auth/**"),
                    new AntPathRequestMatcher("/forgot-password/**"), // ✅ Corrected typo
                    new AntPathRequestMatcher("/authorize/**"),
                    new AntPathRequestMatcher("/authenticate/**"),
                    new AntPathRequestMatcher("/**", "OPTIONS") // ✅ Allow CORS preflight
                ).permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic().disable()
            .formLogin().disable()
            .logout().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ✅ Use exact origin if possible when allowCredentials is true
        configuration.setAllowedOrigins(Arrays.asList("http://147.79.66.20:2010"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*")); // Accept all headers
        configuration.setAllowCredentials(true); // Allow cookies, Authorization headers, etc.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
