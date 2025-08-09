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
	                new AntPathRequestMatcher("/forgort-password/**"),
	                new AntPathRequestMatcher("/authorize/**"),
	                new AntPathRequestMatcher("/authenticate/**")
	            ).permitAll()
	            .requestMatchers(new AntPathRequestMatcher("/**", "OPTIONS")).permitAll()
	            .anyRequest().authenticated()
	        )
	        .httpBasic().disable()               // ⛔ Disable HTTP Basic Auth
	        .formLogin().disable()               // ⛔ Disable form login
	        .logout().disable()                  // ⛔ Optional, disable logout endpoint
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	    return http.build();
	}

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // Your frontend origin
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}