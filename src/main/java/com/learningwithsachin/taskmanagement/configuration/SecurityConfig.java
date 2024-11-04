package com.learningwithsachin.taskmanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf().disable() // Optional: Disable CSRF if it's not needed for testing
				.authorizeRequests()
				.antMatchers("/public/**", "/api/v1/register", "/api/v1/login") // Define routes to be publicly accessible
				.permitAll()
				.anyRequest()
				.authenticated();
		return http.build();
	}
}
