package com.rohin.Security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuth implements AuthenticationProvider
{
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserDetailsService uds;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String name= authentication.getName();
		String password= String.valueOf(authentication.getCredentials());
		UserDetails ud= uds.loadUserByUsername(name);
		if(ud!=null && encoder.matches(password,ud.getPassword()))
			return new UsernamePasswordAuthenticationToken(name, password, Arrays.asList());
		else
			throw new AuthenticationCredentialsNotFoundException("User not found");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		 return UsernamePasswordAuthenticationToken.class
				 .isAssignableFrom(authentication);
	}

}
