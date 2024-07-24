package com.java.webdevelopment.shopping_app.services.impl;


import org.springframework.data.domain.Page;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.conventers.RoleListConventer;
import com.java.webdevelopment.shopping_app.entities.Role;
import com.java.webdevelopment.shopping_app.entities.User;
import com.java.webdevelopment.shopping_app.exceptions.EmailAlreadyExistException;
import com.java.webdevelopment.shopping_app.exceptions.RoleNotFoundException;
import com.java.webdevelopment.shopping_app.exceptions.SelfDeleteAdminException;
import com.java.webdevelopment.shopping_app.exceptions.UserNotFoundException;
import com.java.webdevelopment.shopping_app.exceptions.UsernameAlreadyExistException;
import com.java.webdevelopment.shopping_app.payload.UserDTO;
import com.java.webdevelopment.shopping_app.payload.requests.UserRequest;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.UserProfileResponse;
import com.java.webdevelopment.shopping_app.repositories.RoleRepository;
import com.java.webdevelopment.shopping_app.repositories.UserRepository;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;
import com.java.webdevelopment.shopping_app.services.UserService;
import com.java.webdevelopment.shopping_app.utils.IdUtil;
import com.java.webdevelopment.shopping_app.utils.PageUtil;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    
    private final ModelMapper modelMapper;
    
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;

        TypeMap<User, UserProfileResponse> userToProfileResponse = modelMapper.createTypeMap(User.class, UserProfileResponse.class);
        userToProfileResponse.addMappings(
            mapper -> mapper.using(new RoleListConventer()).map(User::getRoles, UserProfileResponse::setRoles)
        );

        TypeMap<UserDTO, User>  userDtoToUser = modelMapper.createTypeMap(UserDTO.class, User.class);
        userDtoToUser.addMappings(
            mapper -> mapper.skip(User::setRoles)
        );
    }

    @Override
	public UserProfileResponse getCurrentUser(UserPrincipal userPrincipal) {
		
		String username = userPrincipal.getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
        return modelMapper.map(user, UserProfileResponse.class);
	}

    @Override
    public PageResponse<UserProfileResponse> getPaginateUser(Integer page, Integer pageSize) {

        Page<User> users = userRepository.findAll(PageUtil.request(page, pageSize));
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
    public UserProfileResponse createUser(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String email = userDTO.getEmail();

        if(isUsernameExists(username)) {
            throw new UsernameAlreadyExistException(username);
        }

        if(isEmailExists(email)) {
            throw new EmailAlreadyExistException(email);
        }

        User user = modelMapper.map(userDTO, User.class);
        user.setId(IdUtil.generate());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        for (String roleCode : userDTO.getRoles()) {            
            Role role = roleRepository.findByCode(roleCode)
                .orElseThrow(() -> new RoleNotFoundException(roleCode));
            user.addRole(role);
        }

        if (!user.hasRoles()) {
            Role role = roleRepository.findByCode("USER")
                    .orElseThrow(() -> new RoleNotFoundException("USER"));
            user.addRole(role);
        }

        User insertedUser = userRepository.save(user);
        return modelMapper.map(insertedUser, UserProfileResponse.class);
    }

    @Override
    public UserProfileResponse createUser(UserRequest request) {
        UserDTO userDTO = modelMapper.map(request, UserDTO.class);
        return createUser(userDTO);
    }

    @Override
    public UserProfileResponse updateUser(String id, UserDTO newUser) {

        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));            

        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserProfileResponse.class);
    }

    @Override
    public UserProfileResponse updateProfile(UserPrincipal userPrincipal, UserDTO newUser) {

        String id = userPrincipal.getId();
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserProfileResponse.class);
    }

    @Override
    public ApiResponse deleteUser(String id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        boolean isUserAdmin = user.isAdmin();
        if (isUserAdmin) {
            throw new SelfDeleteAdminException();
        }
        userRepository.delete(user);

        return new ApiResponse(
                true,
                Contants.USER_DELETED_SUCCESS(user.getUsername()),
                HttpStatus.OK);
    }

    @Override
    public ApiResponse selfDelete(UserPrincipal userPrincipal) {
        String id = userPrincipal.getId();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());
        userRepository.delete(user);

        return new ApiResponse(
                true,
                Contants.USER_DELETED_SUCCESS(user.getUsername()),
                HttpStatus.OK);
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
