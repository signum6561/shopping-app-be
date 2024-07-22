package com.java.webdevelopment.shopping_app.services.impl;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.webdevelopment.shopping_app.entities.User;
import com.java.webdevelopment.shopping_app.exceptions.UserNotFoundException;
import com.java.webdevelopment.shopping_app.repositories.UserRepository;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;
import com.java.webdevelopment.shopping_app.services.UserAuthService;

@Service
public class UserAuthServiceImpl implements UserDetailsService, UserAuthService {

    private final UserRepository userRepository;

    public UserAuthServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		UserPrincipal userPrincipal = new UserPrincipal(user);
		userPrincipal.setAuthorities(user.getRoles());
		return new UserPrincipal(user);
    }

	@Override
	@Transactional
	public UserPrincipal loadUserById(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException());
		UserPrincipal userPrincipal = new UserPrincipal(user);
		userPrincipal.setAuthorities(user.getRoles());
		return userPrincipal;
	}

}
