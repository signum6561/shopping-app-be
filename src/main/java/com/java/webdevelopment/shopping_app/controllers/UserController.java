package com.java.webdevelopment.shopping_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.payload.UserDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.UserProfileResponse;
import com.java.webdevelopment.shopping_app.sercurity.CurrentUser;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;
import com.java.webdevelopment.shopping_app.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/user")
@SecurityRequirement(name = "shopping-api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile")
	public ResponseEntity<UserProfileResponse> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		UserProfileResponse response = userService.getCurrentUser(userPrincipal);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/profile")
	public ResponseEntity<UserProfileResponse> updateUserProfile(
			@Valid @RequestBody UserDTO updateUser, 
			@CurrentUser UserPrincipal currentUser) {
		UserProfileResponse response = userService.updateProfile(currentUser, updateUser);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/")
	@PreAuthorize("@authz.permission(#root, 'read:user')")
	public ResponseEntity<PageResponse<UserProfileResponse>> getAllUser(
		@RequestParam(value = "page", required = false, defaultValue =  Contants.DEFAULT_PAGE_INDEX) Integer page,
		@RequestParam(value = "size", required = false, defaultValue = Contants.DEFAULT_PAGE_SIZE) Integer pageSize
	) {
		PageResponse<UserProfileResponse> response = userService.getPaginateUser(page, pageSize);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	@PreAuthorize("@authz.permission(#root, 'read:user')")
	public ResponseEntity<UserProfileResponse> getUser(@PathVariable("userId") String userId) {
		UserProfileResponse response = userService.getUser(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{userId}")
	@PreAuthorize("@authz.permission(#root, 'update:user')")
	public ResponseEntity<UserProfileResponse> updateUser(
			@PathVariable("userId") String userId,
			@Valid @RequestBody UserDTO updateUser, 
			@CurrentUser UserPrincipal currentUser) {
		UserProfileResponse response = userService.updateUser(userId, updateUser);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("@authz.permission(#root, 'delete:user')")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") String userId) {
		ApiResponse response = userService.deleteUser(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/self-delete")
	@PreAuthorize("!hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> selfDeleteUser(@CurrentUser UserPrincipal userPrincipal) {
		ApiResponse response = userService.selfDelete(userPrincipal);
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
