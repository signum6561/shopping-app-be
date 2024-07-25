package com.java.webdevelopment.shopping_app.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.java.webdevelopment.shopping_app.sercurity.JWTAuthEntryPoint;
import com.java.webdevelopment.shopping_app.sercurity.JWTAuthFilter;

@Configuration
@EnableWebSecurity
public class SercurityConfig {

    @Autowired
	private JWTAuthEntryPoint unauthorizedHandler;

    public String[] PUBLIC_ROUTES = {
        "/api/v1/auth/**", 
        "/v3/api-docs/**", 
        "/swagger-ui/**",
        "/api/v1/user/email-exist",
        "/api/v1/user/username-exist",
        "/api/v1/category/o/**",
        "/api/v1/product/o/**",
    };
	
	@Bean
	JWTAuthFilter JWTAuthFilter() {
	    return new JWTAuthFilter();
	}
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .disable())
                .cors(cors -> cors
                        .disable())
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(unauthorizedHandler))
                .addFilterBefore(JWTAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                            .requestMatchers(PUBLIC_ROUTES)
                            .permitAll()
                            .anyRequest()
                            .authenticated());

        return http.build();
    }
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
    }

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
