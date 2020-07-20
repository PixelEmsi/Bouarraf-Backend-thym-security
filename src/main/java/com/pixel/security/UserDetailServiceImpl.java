package com.pixel.security;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pixel.dao.UserRepository;
import com.pixel.entities.User;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private  UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = this.userRepository.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("Cannot find username");
		}
		return new UserPrincipal(user);
	}

}
