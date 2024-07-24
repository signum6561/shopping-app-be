package com.java.webdevelopment.shopping_app.services;

import com.java.webdevelopment.shopping_app.payload.RoleDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.DataResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.RoleInfoResponse;

public interface RoleService {
    
    PageResponse<RoleInfoResponse> getPaginateRole(Integer page, Integer pageSize);

    DataResponse<RoleInfoResponse> getAllRole();

    RoleDTO getRole(String id);

    RoleDTO createRole(RoleDTO roleDTO);

    RoleDTO updateRole(RoleDTO roleDTO);

    ApiResponse deleteRole(String id);
}
