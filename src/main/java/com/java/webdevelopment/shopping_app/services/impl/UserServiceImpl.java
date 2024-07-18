package com.java.webdevelopment.shopping_app.services.impl;


import org.springframework.data.domain.Page;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.entities.User;
import com.java.webdevelopment.shopping_app.exceptions.AccessDeniedException;
import com.java.webdevelopment.shopping_app.exceptions.EmailAlreadyExistException;
import com.java.webdevelopment.shopping_app.exceptions.UserNotFoundException;
import com.java.webdevelopment.shopping_app.exceptions.UsernameAlreadyExistException;
import com.java.webdevelopment.shopping_app.payload.UserDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.UserProfileResponse;
import com.java.webdevelopment.shopping_app.repositories.UserRepository;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;
import com.java.webdevelopment.shopping_app.services.UserService;
import com.java.webdevelopment.shopping_app.utils.IdUtil;
import com.java.webdevelopment.shopping_app.utils.ValidateUtil;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
	UserRepository userRepository;

    @Autowired
	PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Override
	public UserProfileResponse getCurrentUser(UserPrincipal userPrincipal) {
		
		String username = userPrincipal.getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
        return modelMapper.map(user, UserProfileResponse.class);
	}

    @Override
    public PageResponse<UserProfileResponse> getPaginateUser(Integer page, Integer pageSize) {

        ValidateUtil.validatePaginateParams(page, pageSize);
        Pageable pageable = PageRequest.of(page-1, pageSize);
        Page<User> users = userRepository.findAll(pageable);
        List<UserProfileResponse> userResponse = users.get()
            .map(user -> modelMapper.map(user, UserProfileResponse.class))
            .toList();

        PageResponse<UserProfileResponse> pageResponse = new PageResponse<>();
        pageResponse.setCurrentPage(page);
        pageResponse.setLastPage(users.getTotalPages());
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotal(users.getNumberOfElements());
        pageResponse.setData(userResponse);
        return pageResponse; 
    }

    @Override
    public UserProfileResponse getUser(String id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException());
		return modelMapper.map(user, UserProfileResponse.class);
    }

    @Override
    public UserProfileResponse createUser(UserDTO request) {
        String username = request.getUsername();
        String email = request.getEmail();

        if(isUsernameExists(username)) {
            throw new UsernameAlreadyExistException(username);
        }

        if(isEmailExists(email)) {
            throw new EmailAlreadyExistException(email);
        }

        User user = modelMapper.map(request, User.class);
        user.setId(IdUtil.generate());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return modelMapper.map(user, UserProfileResponse.class);
    }

    @Override
    public UserProfileResponse updateUser(String id, UserDTO newUser, UserPrincipal authUser) {

        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

        if(!id.equals(authUser.getId()) && !user.isAdmin()) {
            throw new AccessDeniedException();
        }

        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        userRepository.save(user);
        return modelMapper.map(user, UserProfileResponse.class);
    }

    @Override
    public ApiResponse deleteUser(String id) {
        
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException());
        userRepository.delete(user);

        return new ApiResponse(
            true,
            Contants.USER_DELETED_SUCCESS(user.getUsername()),
            HttpStatus.OK
        );
    }

    @Override
    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

}
