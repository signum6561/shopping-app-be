package com.java.webdevelopment.shopping_app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.interfaces.CurrentUser;
import com.java.webdevelopment.shopping_app.interfaces.HasPermission;
import com.java.webdevelopment.shopping_app.payload.requests.UserInfoRequest;
import com.java.webdevelopment.shopping_app.payload.requests.UserOrderRequest;
import com.java.webdevelopment.shopping_app.payload.requests.UserRequest;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.OrderResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.UserResponse;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;
import com.java.webdevelopment.shopping_app.services.OrderService;
import com.java.webdevelopment.shopping_app.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/user")
@SecurityRequirement(name = "shopping-api")
public class UserController {

	private final UserService userService;

	private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
		this.userService = userService;
		this.orderService = orderService;
	}

	@GetMapping("/profile")
	public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		UserResponse response = userService.getCurrentUser(userPrincipal);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/profile")
	public ResponseEntity<UserResponse> updateUserProfile(
			@Valid @RequestBody UserInfoRequest updateUser, 
			@CurrentUser UserPrincipal currentUser) {
		UserResponse response = userService.updateProfile(currentUser, updateUser);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/profile/orders")
	@HasPermission("read_own:order")
	public ResponseEntity<PageResponse<OrderResponse>> getCurrentUserOrders(
		@RequestParam(required = false, defaultValue =  Contants.DEFAULT_PAGE_INDEX) Integer page,
		@RequestParam(required = false, defaultValue = Contants.DEFAULT_PAGE_SIZE) Integer perPage,
		@CurrentUser UserPrincipal userPrincipal
	) {
		PageResponse<OrderResponse> response = orderService.getCurrentUserOrders(userPrincipal, page, perPage);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/profile/orders")
	@HasPermission("create_own:order")
	public ResponseEntity<OrderResponse> createUserOrder(
		@Valid @RequestBody UserOrderRequest newOrder,
		@CurrentUser UserPrincipal userPrincipal
	) {
		OrderResponse response = orderService.createCurrentUserOrder(newOrder, userPrincipal);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/profile/orders/{orderId}")
	@HasPermission("delete_own:order")
	public ResponseEntity<ApiResponse> deleteCurrentUserOrder(
		@PathVariable String orderId,
		@CurrentUser UserPrincipal userPrincipal
	) {
		ApiResponse response = orderService.deleteCurrentUserOrder(orderId, userPrincipal);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/")
	@HasPermission("read_all:user")
	public ResponseEntity<PageResponse<UserResponse>> getAllUser(
		@RequestParam(required = false, defaultValue =  Contants.DEFAULT_PAGE_INDEX) Integer page,
		@RequestParam(required = false, defaultValue = Contants.DEFAULT_PAGE_SIZE) Integer perPage
	) {
		PageResponse<UserResponse> response = userService.getPaginateUser(page, perPage);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{userId}")
	@HasPermission("read_all:user")
	public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
		UserResponse response = userService.getUser(userId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/")
	@HasPermission("create:user")
	public ResponseEntity<UserResponse> createUser(
			@Valid @RequestBody UserRequest newUser
		) {
		UserResponse response = userService.createUser(newUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{userId}")
	@HasPermission("update:user")
	public ResponseEntity<UserResponse> updateUser(
			@PathVariable String userId,
			@Valid @RequestBody UserRequest updateUser, 
			@CurrentUser UserPrincipal currentUser) {
		UserResponse response = userService.updateUser(userId, updateUser);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{userId}")
	@HasPermission("delete:user")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
		ApiResponse response = userService.deleteUser(userId);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/self-delete")
	public ResponseEntity<ApiResponse> selfDeleteUser(@CurrentUser UserPrincipal userPrincipal) {
		ApiResponse response = userService.selfDelete(userPrincipal);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/username-exist")
	public ResponseEntity<Boolean> checkUsernameUnique(@RequestParam String username) {
		Boolean response = userService.isUsernameExists(username);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/email-exist")
	public ResponseEntity<Boolean> checkEmailUnique(@RequestParam String email) {
		Boolean response = userService.isEmailExists(email);
		return ResponseEntity.ok(response);
	}
	
}
