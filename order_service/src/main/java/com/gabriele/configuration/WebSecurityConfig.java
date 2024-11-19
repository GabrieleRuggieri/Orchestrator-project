
//	public final static String[] PUBLIC_REQUEST_MATCHERS = { "/api/v1/auth/**", "/api-docs/**", "/swagger-ui/**" };

package com.gabriele.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disabilita CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()) // Consenti l'accesso a tutte le richieste
                .logout(AbstractHttpConfigurer::disable) // Disabilita il logout
                .formLogin(AbstractHttpConfigurer::disable) // Disabilita il form login
                .httpBasic(AbstractHttpConfigurer::disable); // Disabilita HTTP Basic Authentication

        return http.build();
    }
}