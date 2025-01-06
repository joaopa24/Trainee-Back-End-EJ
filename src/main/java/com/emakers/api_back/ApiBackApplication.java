package com.emakers.api_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication ( exclude = {SecurityAutoConfiguration.class} )
@EnableJpaRepositories(basePackages = "com.emakers.api_back.repository")
public class ApiBackApplication {

    public static void main(String[] args) {
        // Testar a conexão com o banco de dados
        String url = "jdbc:postgresql://localhost:5432/api-back"; // Substitua com sua URL de conexão
        String user = "postgres"; // Substitua com seu usuário
        String password = "123"; // Substitua com sua senha

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexão bem-sucedida com o banco de dados!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
            // Interrompe a execução da aplicação se a conexão falhar
            System.exit(1);
        }

        // Iniciar a aplicação Spring Boot
        SpringApplication.run(ApiBackApplication.class, args);
    }

}