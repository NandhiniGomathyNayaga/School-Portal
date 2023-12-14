package com.school.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.JwtUtil;
import com.school.model.User;
import com.school.model.UserRequest;
import com.school.model.UserResponse;
import com.school.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService us;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody User user){
		
		Integer id=us.saveUser(user);
		String body="User '"+id+"'saved";
		
		return ResponseEntity.ok(body);
    }
	
	
	//validate user and generate token(login)
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request){
		// TODO : validate un/pwd with database
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
				(request.getUsername(),request.getPassword()));
		String token = util.generateToken(request.getUsername());
		
		return ResponseEntity.ok(new UserResponse(token,"Success! Generated by aashana"));
	}
	
   //3. after login only
	@PostMapping("/welcome")
	public ResponseEntity<String> accessData(Principal p){
		return ResponseEntity.ok("Hello User!" + p.getName());
	}
	
}
