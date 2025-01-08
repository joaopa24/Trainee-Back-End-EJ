package com.emakers.api_back.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Definição da configuração de segurança sem WebSecurityConfigurerAdapter
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Desabilita o CSRF (normalmente usado em APIs REST)
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    // Permite acesso sem autenticação ao endpoint "/livro/registrar"
                    .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()  
                    .requestMatchers(HttpMethod.GET, "/auth/current").permitAll()  
                    // Exige autenticação para todas as outras requisições
                    .anyRequest().authenticated()  
            );

        return http.build();
    }

    // Bean para o PasswordEncoder que usará o BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean para AuthenticationManager, necessário para autenticação
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

