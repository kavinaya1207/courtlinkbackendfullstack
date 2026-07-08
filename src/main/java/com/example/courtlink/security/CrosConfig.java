package com.example.courtlink.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CrosConfig {

    /**
     * CorsFilter bean — runs at Servlet level, BEFORE Spring Security.
     * This ensures CORS headers are present even on endpoints that are
     * excluded from the Security filter chain via webSecurityCustomizer().ignoring().
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow all origins so file:// and any localhost port works
        config.setAllowedOriginPatterns(List.of("*"));

        // All HTTP methods including OPTIONS preflight and PATCH
        config.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
        ));

        // Allow all headers including Authorization
        config.setAllowedHeaders(List.of("*"));

        // Expose Authorization header to the browser
        config.setExposedHeaders(List.of("Authorization"));

        // Required when sending credentials (JWT in Authorization header)
        config.setAllowCredentials(true);

        // Cache preflight response for 1 hour
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}