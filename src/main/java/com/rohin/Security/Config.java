package com.rohin.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class Config	extends WebSecurityConfigurerAdapter {
	@Autowired
	AuthenticationProvider custom;
	
	@Autowired
	UserDetailsService uds;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(custom);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		
		 http 
	      .csrf().disable()
	      .authorizeRequests().antMatchers("/register**", "/registers**", "/inbox**", "/verify**", "/resetpassword**", "/forgot**", "/user**")
	      .permitAll() .anyRequest().authenticated() 
	      .and() 
	      .formLogin().loginPage("/login")
	      .permitAll() 
	      .and() 
	      .logout().deleteCookies("JSESSIONID") .invalidateHttpSession(true) 
	      .clearAuthentication(true) .permitAll()
	      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	      .logoutSuccessUrl("/logout-success")
	      .and()
         .rememberMe().key("uniqueAndSecret").userDetailsService(uds);
	      
	}

 
 
	
}


