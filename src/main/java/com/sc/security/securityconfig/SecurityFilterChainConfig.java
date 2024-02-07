package com.sc.security.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityFilterChainConfig {

        @Autowired
        private final CustomAuthenticationEntryPoint authenticationEntryPoint;

        private final JWTAuthenticationFilter jwtAuthenticationFilter;

    public SecurityFilterChainConfig(CustomAuthenticationEntryPoint authenticationEntryPoint, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

            httpSecurity.cors(AbstractHttpConfigurer::disable);
            httpSecurity.csrf(AbstractHttpConfigurer::disable);

            httpSecurity.authorizeHttpRequests(request ->
                    request
                            .requestMatchers("/swagger-ui/**")
                            .permitAll()
                            .requestMatchers("/apis/**").permitAll()
                            .requestMatchers("/api/auth/sign-up", "/api/auth/login").permitAll()
                            .requestMatchers("/api/moderator/**").authenticated()
                            .requestMatchers("/api/admin/**").authenticated()
                            .anyRequest().authenticated()
            );

            httpSecurity.exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint));
            httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class);

            return httpSecurity.build();

        }

}
