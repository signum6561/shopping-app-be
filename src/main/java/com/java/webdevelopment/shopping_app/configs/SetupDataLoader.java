package com.java.webdevelopment.shopping_app.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.java.webdevelopment.shopping_app.entities.Role;
import com.java.webdevelopment.shopping_app.entities.User;
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
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {

        if(isAlreadySetup) {
            return;
        }

        Role adminRole = createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("MODERATOR");
        createRoleIfNotFound("USER");

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
    private Role createRoleIfNotFound(String name) {
 
        Role role = roleRepository.findByName(name).orElse(null);
        if (role == null) {
            role = Role.builder()
                .id(IdUtil.generate())
                .name(name)
                .build();
            return roleRepository.save(role);
        }
        return role;
    }

}
