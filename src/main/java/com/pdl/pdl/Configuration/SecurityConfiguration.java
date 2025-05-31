package com.pdl.pdl.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	@SuppressWarnings("removal")
	@Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	         http
	         .csrf()
	 	     .disable()
	         .authorizeHttpRequests()
	 	     .requestMatchers("/auth/**").permitAll()
         .requestMatchers("/api/**").permitAll()
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
