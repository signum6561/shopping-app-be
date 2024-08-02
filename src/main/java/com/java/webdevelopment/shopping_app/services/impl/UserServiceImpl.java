package com.java.webdevelopment.shopping_app.services.impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.conventers.RoleNameListConventer;
import com.java.webdevelopment.shopping_app.entities.Role;
import com.java.webdevelopment.shopping_app.entities.User;
import com.java.webdevelopment.shopping_app.enums.BaseRole;
import com.java.webdevelopment.shopping_app.exceptions.AccessDeniedException;
import com.java.webdevelopment.shopping_app.exceptions.EmailAlreadyExistException;
import com.java.webdevelopment.shopping_app.exceptions.RoleNotFoundException;
import com.java.webdevelopment.shopping_app.exceptions.SystemAdminDeleteException;
import com.java.webdevelopment.shopping_app.exceptions.UserNotFoundException;
import com.java.webdevelopment.shopping_app.exceptions.UsernameAlreadyExistException;
import com.java.webdevelopment.shopping_app.payload.requests.UserInfoRequest;
import com.java.webdevelopment.shopping_app.payload.requests.UserRequest;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.UserResponse;
import com.java.webdevelopment.shopping_app.repositories.RoleRepository;
import com.java.webdevelopment.shopping_app.repositories.UserRepository;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;
import com.java.webdevelopment.shopping_app.services.UserService;
import com.java.webdevelopment.shopping_app.utils.IdUtil;
import com.java.webdevelopment.shopping_app.utils.PageUtil;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    
    private final ModelMapper modelMapper;

    @Value("${app.sys-admin.username}")
    public String SYS_ADMIN_USERNAME;

    
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<User, UserResponse> entityToProfileResponse = modelMapper.createTypeMap(User.class,
                UserResponse.class);
        entityToProfileResponse.addMappings(
                mapper -> mapper.using(new RoleNameListConventer()).map(User::getRoles, UserResponse::setRoleNames));
    }

    @Override
    @Transactional(readOnly = true)
	public UserResponse getCurrentUser(UserPrincipal userPrincipal) {
		
		String username = userPrincipal.getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
        return modelMapper.map(user, UserResponse.class);
	}

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getPaginateUser(Integer page, Integer pageSize) {

        Page<User> users = userRepository.findAll(PageUtil.request(page, pageSize));
        List<UserResponse> userResponse = users.get()
            .map(user -> modelMapper.map(user, UserResponse.class))
            .toList();

        PageResponse<UserResponse> pageResponse = new PageResponse<>();
        pageResponse.setCurrentPage(page);
        pageResponse.setLastPage(users.getTotalPages());
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotal(users.getNumberOfElements());
        pageResponse.setData(userResponse);
        return pageResponse; 
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getUserByRoleCode(String code, Integer page, Integer pageSize) {

        boolean isRoleCodeExist = roleRepository.existsByCode(code);
        if(!isRoleCodeExist) {
            throw new RoleNotFoundException(code);
        }

        Pageable pageable = PageUtil.request(page, pageSize);
        Page<User> users = userRepository.findByRolesCode(code, pageable);
        List<UserResponse> userResponse = users.get()
            .map(user -> modelMapper.map(user, UserResponse.class))
            .toList();

        PageResponse<UserResponse> pageResponse = new PageResponse<>();
        pageResponse.setCurrentPage(page);
        pageResponse.setLastPage(users.getTotalPages());
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotal(users.getNumberOfElements());
        pageResponse.setData(userResponse);
        return pageResponse;
    }

    @Override
    @Transactional
    public UserResponse getUser(String id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException());
		return modelMapper.map(user, UserResponse.class);
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {
        String username = request.getUsername();
        String email = request.getEmail();

        if(isUsernameExists(username)) {
            throw new UsernameAlreadyExistException(username);
        }

        if(isEmailExists(email)) {
            throw new EmailAlreadyExistException(email);
        }

        User user = User.builder()
            .id(IdUtil.generate())
            .username(username)
            .email(email)
            .password(passwordEncoder.encode(request.getPassword()))
            .build();
        
        if(!request.getRoleCodes().isEmpty()) {
            for (String roleCode : request.getRoleCodes()) {            
                Role role = roleRepository.findByCode(roleCode)
                    .orElseThrow(() -> new RoleNotFoundException(roleCode));
                user.addRole(role);
            }
        }

        User insertedUser = userRepository.save(user);
        return modelMapper.map(insertedUser, UserResponse.class);
    }

    @Override
    @Transactional
    public UserResponse createUser(UserInfoRequest request) {
        String username = request.getUsername();
        String email = request.getEmail();

        if(isUsernameExists(username)) {
            throw new UsernameAlreadyExistException(username);
        }

        if(isEmailExists(email)) {
            throw new EmailAlreadyExistException(email);
        }

        User user = User.builder()
            .id(IdUtil.generate())
            .username(username)
            .email(email)
            .password(passwordEncoder.encode(request.getPassword()))
            .build();

        String basicRole = BaseRole.USER.toString();
        Role role = roleRepository.findByCode(basicRole)
                .orElseThrow(() -> new RoleNotFoundException(basicRole));
        user.addRole(role);

        User insertedUser = userRepository.save(user);
        return modelMapper.map(insertedUser, UserResponse.class);
    }

    @Override
    @Transactional
    public UserResponse updateUser(String id, UserRequest newUser) {

        String username = newUser.getUsername();
        String email = newUser.getEmail();

        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

        if(user.getUsername().equals(SYS_ADMIN_USERNAME)) {
            throw new AccessDeniedException();
        }
            
        if(!user.getUsername().equals(username) && isUsernameExists(username)) {
            throw new UsernameAlreadyExistException(username);
        }

        if(!user.getEmail().equals(email) && isEmailExists(email)) {
            throw new EmailAlreadyExistException(email);
        }

        Set<String> roleCodes = newUser.getRoleCodes();
        if(roleCodes.isEmpty()) {
            roleCodes.add(BaseRole.USER.toString());
        }

        user.clearRoles();
        for (String roleCode : roleCodes) {            
            Role role = roleRepository.findByCode(roleCode)
                .orElseThrow(() -> new RoleNotFoundException(roleCode));
            user.addRole(role);
        }

        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Override
    @Transactional
    public UserResponse updateProfile(UserPrincipal userPrincipal, UserInfoRequest newUser) {

        String id = userPrincipal.getId();
        String username = newUser.getUsername();
        String email = newUser.getEmail();

        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
        
        if(user.getUsername().equals(SYS_ADMIN_USERNAME)) {
            throw new AccessDeniedException();
        }

        if(!user.getUsername().equals(username) && isUsernameExists(username)) {
            throw new UsernameAlreadyExistException(username);
        }

        if(!user.getEmail().equals(email) && isEmailExists(email)) {
            throw new EmailAlreadyExistException(email);
        }

        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Override
    @Transactional
    public ApiResponse deleteUser(String id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        boolean isUserSysAdmin = user.getUsername().equals(SYS_ADMIN_USERNAME);
        if (isUserSysAdmin) {
            throw new SystemAdminDeleteException();
        }
        userRepository.delete(user);

        return new ApiResponse(
                true,
                Contants.USER_DELETED_SUCCESS(user.getUsername()),
                HttpStatus.OK);
    }

    @Override
    @Transactional
    public ApiResponse selfDelete(UserPrincipal userPrincipal) {
        String id = userPrincipal.getId();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        boolean isUserSysAdmin = user.getUsername().equals(SYS_ADMIN_USERNAME);
        if (isUserSysAdmin) {
            throw new SystemAdminDeleteException();
        }

        userRepository.delete(user);

        return new ApiResponse(
                true,
                Contants.USER_DELETED_SUCCESS(user.getUsername()),
                HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

}
