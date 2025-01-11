package com.emakers.api_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.emakers.api_back.auth.SecurityConfig;

@SpringBootApplication
@Import(SecurityConfig.class)
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//SpringBootApplication ( exclude = {SecurityAutoConfiguration.class} )
@EnableJpaRepositories(basePackages = "com.emakers.api_back.repository")
public class ApiBackApplication {

    public static void main(String[] args) {
        // Iniciar a aplicação Spring Boot
        SpringApplication.run(ApiBackApplication.class, args);
    }
}
