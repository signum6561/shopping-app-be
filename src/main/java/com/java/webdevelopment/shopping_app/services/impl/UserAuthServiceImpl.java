package com.java.webdevelopment.shopping_app.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.webdevelopment.shopping_app.entities.User;
import com.java.webdevelopment.shopping_app.exceptions.UserNotFoundException;
import com.java.webdevelopment.shopping_app.repositories.UserRepository;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;
import com.java.webdevelopment.shopping_app.services.UserAuthService;

@Service
public class UserAuthServiceImpl implements UserDetailsService, UserAuthService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		return UserPrincipal.create(user);
    }

	@Override
	public UserPrincipal loadUserById(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException());
		return UserPrincipal.create(user);
	}

}
