package com.java.webdevelopment.shopping_app.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(
    prePostEnabled = true
)
public class AppConfig {

    @Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
    
}
