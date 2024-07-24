package com.java.webdevelopment.shopping_app.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.entities.Role;
import com.java.webdevelopment.shopping_app.exceptions.RoleNotFoundException;
import com.java.webdevelopment.shopping_app.payload.RoleDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.DataResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.repositories.RoleRepository;
import com.java.webdevelopment.shopping_app.services.RoleService;
import com.java.webdevelopment.shopping_app.utils.IdUtil;
import com.java.webdevelopment.shopping_app.utils.PageUtil;

public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PageResponse<RoleDTO> getPaginateRole(Integer page, Integer pageSize) {
        Page<Role> roles = roleRepository.findAll(PageUtil.request(page, pageSize));
        List<RoleDTO> roleDTOs = roles.get()
            .map(role -> modelMapper.map(role, RoleDTO.class))
            .toList();
        PageResponse<RoleDTO> pageResponse = new PageResponse<>();
        pageResponse.setCurrentPage(page);
        pageResponse.setLastPage(roles.getTotalPages());
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotal(roles.getNumberOfElements());
        pageResponse.setData(roleDTOs);
        return pageResponse;
    }

    @Override
    public DataResponse<RoleDTO> getAllRole() {
        List<RoleDTO> roleDTOs = roleRepository.findAll()
            .stream()
            .map(role -> modelMapper.map(role, RoleDTO.class))
            .toList();
        DataResponse<RoleDTO> response = new DataResponse<>(roleDTOs);
        return response;
    }

    @Override
    public RoleDTO getRole(String id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new RoleNotFoundException(id));
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        role.setId(IdUtil.generate());
        Role insertedRole = roleRepository.save(role);
        return modelMapper.map(insertedRole, RoleDTO.class);
    }

    @Override
    public RoleDTO updateRole(RoleDTO roleDTO) {
        String id = roleDTO.getId();
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new RoleNotFoundException(id));
        
        role.setName(roleDTO.getName());
        role.setCode(roleDTO.getCode());
        Role updatedRole = roleRepository.save(role);
        return modelMapper.map(updatedRole, RoleDTO.class);
    }

    @Override
    public ApiResponse deleteRole(String id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new RoleNotFoundException(id));
        roleRepository.delete(role);
        
        return new ApiResponse(
            true,
            Contants.CATEGORY_DELETED_SUCCESS,
            HttpStatus.OK
        );
    }
    
}
