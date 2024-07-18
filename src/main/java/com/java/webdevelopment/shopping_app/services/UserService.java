package com.java.webdevelopment.shopping_app.services;

import com.java.webdevelopment.shopping_app.entities.User;
import com.java.webdevelopment.shopping_app.payload.requests.UserRequest;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.UserProfileResponse;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;

public interface UserService {

    PageResponse<UserProfileResponse> getPaginateUser(Integer page, Integer pageSize);
    
    UserProfileResponse getCurrentUser(UserPrincipal userPrincipal);

    UserProfileResponse getUser(String id);
    
    UserProfileResponse createUser(UserRequest request);

    UserProfileResponse updateUser(String id, User newUser, UserPrincipal authUser);

    ApiResponse deleteUser(String id);

    boolean isUsernameExists(String username);

	boolean isEmailExists(String email);

}
