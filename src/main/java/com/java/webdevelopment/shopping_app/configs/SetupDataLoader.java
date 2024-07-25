package com.java.webdevelopment.shopping_app.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.java.webdevelopment.shopping_app.entities.Permission;
import com.java.webdevelopment.shopping_app.entities.Role;
import com.java.webdevelopment.shopping_app.entities.User;
import com.java.webdevelopment.shopping_app.enums.BaseRole;
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

    @Value("${app.sys-admin.username}")
    public String sysAdminUsername;

    @Value("${app.sys-admin.email}")
    public  String sysAdminEmail;

    @Value("${app.sys-admin.password}")
    public String sysAdminPassword;

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {

        if(isAlreadySetup) {
            return;
        }

        Role adminRole = createRoleIfNotFound(BaseRole.ADMIN.toString(), "System Admin");
        Role userRole = createRoleIfNotFound(BaseRole.USER.toString(), "User");

        for (Resource resource : Resource.values()) {
            String resourceCode = Resource.convertToCode(resource);
            for (String permission : resource.getPermissions()) {
                createPermissionIfNotFound(permission + resourceCode); 
            }
        }

        for (String permssionCode : BaseRole.USER.getPermissions()) {
            Permission permission = createPermissionIfNotFound(permssionCode);
            userRole.addPermission(permission);
        }

        roleRepository.save(userRole);

        if(userRepository.existsByUsername(sysAdminUsername)) {
            isAlreadySetup = true;
            return;
        }

        User adminUser = User.builder()
            .id(IdUtil.generate())
            .username("admin")
            .password(passwordEncoder.encode(sysAdminPassword))
            .email(sysAdminEmail)
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
