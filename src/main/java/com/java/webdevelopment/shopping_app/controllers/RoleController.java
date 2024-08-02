package com.java.webdevelopment.shopping_app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.payload.RoleDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.DataResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.RoleInfoResponse;
import com.java.webdevelopment.shopping_app.payload.responses.UserResponse;
import com.java.webdevelopment.shopping_app.services.RoleService;
import com.java.webdevelopment.shopping_app.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v1/role")
@SecurityRequirement(name = "shopping-api")
public class RoleController {
    
    private final RoleService roleService;

    private final UserService userService;

    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("")
    @PreAuthorize("@authz.permission(#root, 'read_all:role')")
    public ResponseEntity<?> getAllRole(
        @RequestParam(required = false, defaultValue =  Contants.DEFAULT_PAGE_INDEX) Integer page,
		@RequestParam(required = false, defaultValue = Contants.DEFAULT_PAGE_SIZE) Integer perPage,
        @RequestParam(required = false, defaultValue = "false") Boolean all
    ) {
        if(all) {
            DataResponse<RoleInfoResponse> response = roleService.getAllRole();
            return ResponseEntity.ok(response);
        } else {
            PageResponse<RoleInfoResponse> response = roleService.getPaginateRole(page, perPage);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/{roleId}")
    @PreAuthorize("@authz.permission(#root, 'read_all:role')")
    public ResponseEntity<RoleDTO> getRole(@PathVariable String roleId) {
        RoleDTO response = roleService.getRole(roleId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{code}")
    @PreAuthorize("@authz.permission(#root, 'read_all:role')")
    public ResponseEntity<PageResponse<UserResponse>> getUsersByRole(
        @RequestParam(required = false, defaultValue =  Contants.DEFAULT_PAGE_INDEX) Integer page,
		@RequestParam(required = false, defaultValue = Contants.DEFAULT_PAGE_SIZE) Integer perPage,
        @PathVariable String code
    ) {
        PageResponse<UserResponse> response = userService.getUserByRoleCode(code, page, perPage);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    @PreAuthorize("@authz.permission(#root, 'create:role')")
    public ResponseEntity<RoleDTO> createRole(
        @Valid @RequestBody RoleDTO newRole
    ) {
        RoleDTO response = roleService.createRole(newRole);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }
    
    @PutMapping("/{roleId}")
    @PreAuthorize("@authz.permission(#root, 'update:role')")
    public ResponseEntity<RoleDTO> updateRole(
        @Valid @RequestBody RoleDTO updateRole,
        @PathVariable String roleId
    ) {
        RoleDTO response = roleService.updateRole(roleId, updateRole);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{roleId}")
    @PreAuthorize("@authz.permission(#root, 'delete:role')")
    public ResponseEntity<ApiResponse> deleteRole(
        @PathVariable String roleId 
    ) {
        ApiResponse response = roleService.deleteRole(roleId);
        return ResponseEntity.ok(response);
    }
}
