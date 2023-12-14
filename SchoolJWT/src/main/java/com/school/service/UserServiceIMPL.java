package com.school.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.school.model.User;
import com.school.repository.UserRepository;

@Service
public class UserServiceIMPL implements UserService,UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	@Override
	public Integer saveUser(User user) {
		
		//encode password
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		
		return userRepo.save(user).getId();
	}

	//get user by username
	@Override
	public Optional<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		Optional<User> opt=findByUsername(username);
		if(opt.isEmpty()) 
			throw new UsernameNotFoundException("User Not Found");
		
		//read user from db
		User user=opt.get();
		
		return new org.springframework.security.core.userdetails.User(username, 
				user.getPassword(),
				user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList())
				);
	}

	
}
