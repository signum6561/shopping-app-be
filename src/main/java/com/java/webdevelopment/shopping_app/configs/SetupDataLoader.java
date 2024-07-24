package com.java.webdevelopment.shopping_app.configs;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.entities.Permission;
import com.java.webdevelopment.shopping_app.entities.Role;
import com.java.webdevelopment.shopping_app.entities.User;
import com.java.webdevelopment.shopping_app.enums.Resource;
import com.java.webdevelopment.shopping_app.repositories.PermissionRepository;
import com.java.webdevelopment.shopping_app.repositories.RoleRepository;
import com.java.webdevelopment.shopping_app.repositories.UserRepository;
import com.java.webdevelopment.shopping_app.utils.IdUtil;

import jakarta.transaction.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean isAlreadySetup;

    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {

        if(isAlreadySetup) {
            return;
        }

        Role adminRole = createRoleIfNotFound("ADMIN", "System Admin");
        Role userRole = createRoleIfNotFound("USER", "User");

        for (Resource resource : Resource.values()) {
            String resourceName = resource.toString();
            createPermissionIfNotFound("CREATE_" + resourceName);
            createPermissionIfNotFound("READ_" + resourceName);
            createPermissionIfNotFound("UPDATE_" + resourceName);
            createPermissionIfNotFound("DELETE_" + resourceName);
        }

        userRole.setPermissions(new HashSet<>());
        for (String permssionCode : Contants.BASE_USER_PERMISSIONS) {
            Permission permission = createPermissionIfNotFound(permssionCode);
            userRole.addPermission(permission);
        }
        roleRepository.save(userRole);

        if(userRepository.existsByUsername("admin")) {
            isAlreadySetup = true;
            return;
        }

        User adminUser = User.builder()
            .id(IdUtil.generate())
            .username("admin")
            .password(passwordEncoder.encode("missipi6561"))
            .email("quiocdat777@gmail.com")
            .build();
        adminUser.addRole(adminRole);
        userRepository.save(adminUser);
        isAlreadySetup = true;
    }

    @Transactional
    private Role createRoleIfNotFound(String code, String name) {
 
        Role role = roleRepository.findByCode(code).orElse(null);
        if (role == null) {
            role = Role.builder()
                .id(IdUtil.generateCompact())
                .code(code)
                .name(name)
                .build();
            return roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    private Permission createPermissionIfNotFound(String code) {
        Permission permission = permissionRepository.findByCode(code).orElse(null);
        if(permission == null) {
            permission = Permission.builder()
                .id(IdUtil.generateCompact())
                .code(code)
                .build();
            return permissionRepository.save(permission);
        }
        return permission;
    }   

}
