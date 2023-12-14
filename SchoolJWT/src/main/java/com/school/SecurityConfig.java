package com.school;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

	@Autowired
	private InvalidUserAuthEntryPoint invalidUserAuthEntryPoint;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private SecurityFilter securityFilter;
	
	@Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception { 
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		   auth.userDetailsService(userDetailsService)
		   .passwordEncoder(passwordEncoder);
	}
	

	
	@Bean 
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{ 
		http.
		csrf().
		disable()
		.authorizeHttpRequests()
		.requestMatchers("/user/save","/user/login").permitAll()
		.anyRequest().authenticated()
		.and()
        .exceptionHandling()
        .authenticationEntryPoint(invalidUserAuthEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        //register filter for 2nd request onwards..
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        ;
		
		return http.build();
		  
	}
	
}
