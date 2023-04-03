package com.navec.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/***",
            "/v3/api-docs",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/uploads/**",
            "/api/v1/auth/**",
            "/api/v1/filters/**",
            "/api/v1/sections/**",
            "/api/v1/contacts",
            "/api/v1/contacts/**",
            "/api/v1/documents/**",
            "/api/v1/search/**",
            "/api/v1/search",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();

        http.authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST)
                .permitAll();

        http.authorizeHttpRequests()
                        .requestMatchers(HttpMethod.GET, "/api/v1/listings/**")
                        .permitAll();

        http
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
