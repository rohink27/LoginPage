package com.rohin.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import com.rohin.Dao.UserRepository;
import com.rohin.Models.User;

@Component
public class MyUserDetailsService implements UserDetailsService
{
	@Autowired
	UserRepository reppo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user= reppo.findByUsername(username);
		if(user==null)
			 throw new UsernameNotFoundException("User 404");
		 else
			 return new UserDet(user);
		
	}
	

}
