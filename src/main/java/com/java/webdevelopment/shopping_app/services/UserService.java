package com.java.webdevelopment.shopping_app.services;

import com.java.webdevelopment.shopping_app.payload.requests.UserInfoRequest;
import com.java.webdevelopment.shopping_app.payload.requests.UserRequest;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.UserResponse;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;

public interface UserService {

    PageResponse<UserResponse> getPaginateUser(Integer page, Integer pageSize);
    
    UserResponse getCurrentUser(UserPrincipal userPrincipal);

    UserResponse getUser(String id);
    
    UserResponse createUser(UserInfoRequest request);

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(String id, UserRequest newUser);

    UserResponse updateProfile(UserPrincipal userPrincipal, UserInfoRequest newUser);

    ApiResponse deleteUser(String id);

    ApiResponse selfDelete(UserPrincipal userPrincipal);

    boolean isUsernameExists(String username);

	boolean isEmailExists(String email);

}
