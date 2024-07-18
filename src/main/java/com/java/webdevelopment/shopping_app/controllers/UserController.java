package com.java.webdevelopment.shopping_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.entities.User;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.UserProfileResponse;
import com.java.webdevelopment.shopping_app.sercurity.CurrentUser;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;
import com.java.webdevelopment.shopping_app.services.UserService;


@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile")
	public ResponseEntity<UserProfileResponse> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		UserProfileResponse response = userService.getCurrentUser(userPrincipal);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<PageResponse<UserProfileResponse>> getAllUser(
		@RequestParam(value = "page", required = false, defaultValue =  Contants.DEFAULT_PAGE_INDEX) Integer page,
		@RequestParam(value = "size", required = false, defaultValue = Contants.DEFAULT_PAGE_SIZE) Integer pageSize
	) {
		PageResponse<UserProfileResponse> response = userService.getPaginateUser(page, pageSize);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserProfileResponse> getUser(@PathVariable("userId") String userId) {
		UserProfileResponse response = userService.getUser(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserProfileResponse> updateUserByUsername(
			@PathVariable("userId") String userId,
			@RequestBody User updateUser, 
			@CurrentUser UserPrincipal currentUser) {
		UserProfileResponse response = userService.updateUser(userId, updateUser, currentUser);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUserByUsername(@PathVariable("userId") String username) {
		ApiResponse response = userService.deleteUser(username);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/username-exist")
	public ResponseEntity<Boolean> checkUsernameUnique(@RequestParam("username") String username) {
		Boolean unique = userService.isUsernameExists(username);
		return new ResponseEntity<>(unique, HttpStatus.OK);
	}

	@GetMapping("/email-exist")
	public ResponseEntity<Boolean> checkEmailUnique(@RequestParam("email") String email) {

		Boolean unique = userService.isEmailExists(email);
		return new ResponseEntity<>(unique, HttpStatus.OK);
	}
	
}
