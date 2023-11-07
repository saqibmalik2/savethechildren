package com.example.savethechildren.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
		http.
		cors().disable()
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.formLogin().disable();
		
	   http.authorizeHttpRequests(authz -> authz
	            .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll())
	   			.headers(headers -> headers.frameOptions().disable())
	   			.csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")));
		
		return http.build();
	}
	

}
