package com.java.webdevelopment.shopping_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.webdevelopment.shopping_app.payload.requests.LoginRequest;
import com.java.webdevelopment.shopping_app.payload.requests.UserRequest;
import com.java.webdevelopment.shopping_app.payload.responses.JwtResponse;
import com.java.webdevelopment.shopping_app.payload.responses.UserProfileResponse;
import com.java.webdevelopment.shopping_app.sercurity.JWTProvider;
import com.java.webdevelopment.shopping_app.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JWTProvider tokenProvider;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
		UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
		Authentication auth = authManager.authenticate(authReq);
		String token = tokenProvider.generateToken(auth);
		SecurityContextHolder.getContext().setAuthentication(auth);
		return new ResponseEntity<>(new JwtResponse(token,"Bearer"), HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserProfileResponse> register(@Valid @RequestBody UserRequest userRequest) {
		UserProfileResponse response = userService.createUser(userRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
