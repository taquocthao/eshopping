package com.tathao.eshopping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.tathao.eshopping.service.impl.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		// No login required	
		http.authorizeRequests()
			.antMatchers( "/home.html","/login.html", "/logout.html", "/")
			.permitAll();
		
		// admin
		http.authorizeRequests()
				.antMatchers("/admin/**").access("hasAuthority('ADMIN')");
		
		// 403
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403.html");
		
		// form login config
		http.authorizeRequests().and().formLogin()
		.loginPage("/login.html")
		.loginProcessingUrl("/perform_login")
		.defaultSuccessUrl("/")
		.failureUrl("/login.html?error=true")
		.usernameParameter("username")
		.passwordParameter("password")
		.and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
		
	}
	
	
}
