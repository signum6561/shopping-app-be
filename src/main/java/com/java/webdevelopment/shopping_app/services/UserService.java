package com.java.webdevelopment.shopping_app.services;

import com.java.webdevelopment.shopping_app.payload.UserDTO;
import com.java.webdevelopment.shopping_app.payload.requests.UserRequest;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.UserProfileResponse;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;

public interface UserService {

    PageResponse<UserProfileResponse> getPaginateUser(Integer page, Integer pageSize);
    
    UserProfileResponse getCurrentUser(UserPrincipal userPrincipal);

    UserProfileResponse getUser(String id);
    
    UserProfileResponse createUser(UserDTO userDTO);

    UserProfileResponse createUser(UserRequest request);

    UserProfileResponse updateUser(String id, UserDTO newUser);

    UserProfileResponse updateProfile(UserPrincipal userPrincipal, UserDTO newUser);

    ApiResponse deleteUser(String id);

    ApiResponse selfDelete(UserPrincipal userPrincipal);

    boolean isUsernameExists(String username);

	boolean isEmailExists(String email);

}
