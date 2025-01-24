package com.hpms.config;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.hpms.service.IUserService;

@Configuration
@EnableWebSecurity(debug = true)
@EnableTransactionManagement
public class SecurityConfig {
	private final IUserService userService;

	public SecurityConfig(IUserService userService) {
		this.userService = userService;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth.antMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
				.antMatchers("/userlisting/**", "/add_user/**", "/edit_user/**", "/delete_user/**",
						"/doctoravailability")
				.hasRole("ADMIN")
				.antMatchers("/appointments/**", "/transactionrecords/**", "/medicalrecords/**", "/editaccount")
				.hasAnyRole("ADMIN", "USER").anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login").usernameParameter("email")
						.passwordParameter("password").successHandler(authenticationSuccessHandler()) // Custom success
																										// handler
						.permitAll())
				.logout(logout -> logout.logoutUrl("/signout")
						.logoutSuccessUrl("/login").invalidateHttpSession(true).permitAll())
				.csrf();
		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return (request, response, authentication) -> {
			String contextPath = request.getContextPath();
			Set<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toSet());

			if (roles.contains("ROLE_ADMIN")) {
				response.sendRedirect(contextPath + "/userlisting");
			} else {
				response.sendRedirect(contextPath + "/appointments");
			}
		};
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userService)
				.passwordEncoder(passwordEncoder()).and().build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}