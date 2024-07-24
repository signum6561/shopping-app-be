package com.java.webdevelopment.shopping_app.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.conventers.PermissionCodeListConventer;
import com.java.webdevelopment.shopping_app.entities.Permission;
import com.java.webdevelopment.shopping_app.entities.Role;
import com.java.webdevelopment.shopping_app.exceptions.ModifyBaseRoleException;
import com.java.webdevelopment.shopping_app.exceptions.PermissionNotFoundException;
import com.java.webdevelopment.shopping_app.exceptions.RoleCodeAlreadyExistException;
import com.java.webdevelopment.shopping_app.exceptions.RoleNotFoundException;
import com.java.webdevelopment.shopping_app.payload.RoleDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.DataResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.RoleInfoResponse;
import com.java.webdevelopment.shopping_app.repositories.PermissionRepository;
import com.java.webdevelopment.shopping_app.repositories.RoleRepository;
import com.java.webdevelopment.shopping_app.services.RoleService;
import com.java.webdevelopment.shopping_app.utils.IdUtil;
import com.java.webdevelopment.shopping_app.utils.PageUtil;

public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository ,ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.permissionRepository = permissionRepository;

        TypeMap<Role, RoleDTO> roleToRoleDto = modelMapper.createTypeMap(Role.class, RoleDTO.class);
        roleToRoleDto.addMappings(
                mapper -> mapper.using(new PermissionCodeListConventer()).map(Role::getPermissions,
                        RoleDTO::setPermissionCodes));
    }

    @Override
    public PageResponse<RoleInfoResponse> getPaginateRole(Integer page, Integer pageSize) {
        Page<Role> roles = roleRepository.findAll(PageUtil.request(page, pageSize));
        List<RoleInfoResponse> roleInfos = roles.get()
            .map(role -> modelMapper.map(role, RoleInfoResponse.class))
            .toList();

        PageResponse<RoleInfoResponse> pageResponse = new PageResponse<>();
        pageResponse.setCurrentPage(page);
        pageResponse.setLastPage(roles.getTotalPages());
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotal(roles.getNumberOfElements());
        pageResponse.setData(roleInfos);
        return pageResponse;
    }

    @Override
    public DataResponse<RoleInfoResponse> getAllRole() {
        List<RoleInfoResponse> roleInfos = roleRepository.findAll()
            .stream()
            .map(role -> modelMapper.map(role, RoleInfoResponse.class))
            .toList();
        DataResponse<RoleInfoResponse> response = new DataResponse<>(roleInfos);
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
        String code = roleDTO.getCode();
        Role role = Role.builder()
            .id(IdUtil.generate())
            .name(roleDTO.getName())
            .code(code)
            .build();
        
        boolean isRoleCodeExist = roleRepository.existsByCode(code);
        if(isRoleCodeExist) {
            throw new RoleCodeAlreadyExistException(code);
        }
        
        for (String permCode : roleDTO.getPermissionCodes()) {
            Permission permission = permissionRepository.findByCode(permCode)
                .orElseThrow(() -> new PermissionNotFoundException());
            role.addPermission(permission);
        }
        
        Role insertedRole = roleRepository.save(role);
        return modelMapper.map(insertedRole, RoleDTO.class);
    }

    @Override
    public RoleDTO updateRole(RoleDTO roleDTO) {
        String id = roleDTO.getId();
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new RoleNotFoundException(id));

        if(role.isBaseRole()) {
            throw new ModifyBaseRoleException();
        }
        
        role.setName(roleDTO.getName());
        role.setCode(roleDTO.getCode());
        role.clearPermissions();
        for (String permCode : roleDTO.getPermissionCodes()) {
            Permission permission = permissionRepository.findByCode(permCode)
                .orElseThrow(() -> new PermissionNotFoundException());
            role.addPermission(permission);
        }
        
        Role updatedRole = roleRepository.save(role);
        return modelMapper.map(updatedRole, RoleDTO.class);
    }

    @Override
    public ApiResponse deleteRole(String id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new RoleNotFoundException(id));

        if(role.isBaseRole()) {
            throw new ModifyBaseRoleException();
        }

        roleRepository.delete(role);
        
        return new ApiResponse(
            true,
            Contants.ROLE_DELETED_SUCCESS,
            HttpStatus.OK
        );
    }
    
}
