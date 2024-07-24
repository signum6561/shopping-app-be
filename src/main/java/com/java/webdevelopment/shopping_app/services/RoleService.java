package com.java.webdevelopment.shopping_app.services;

import com.java.webdevelopment.shopping_app.payload.RoleDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.DataResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;

public interface RoleService {
    
    PageResponse<RoleDTO> getPaginateRole(Integer page, Integer pageSize);

    DataResponse<RoleDTO> getAllRole();

    RoleDTO getRole(String id);

    RoleDTO createRole(RoleDTO roleDTO);

    RoleDTO updateRole(RoleDTO roleDTO);

    ApiResponse deleteRole(String id);
}
