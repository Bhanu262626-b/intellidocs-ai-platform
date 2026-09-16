package com.example.eka.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

   // Auth is intentionally disabled for local development; no credential store exists yet.
   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

       http
               .csrf(csrf -> csrf.disable())
               .authorizeHttpRequests(auth -> auth
                       .anyRequest().permitAll()
               );

       return http.build();
   }


}
