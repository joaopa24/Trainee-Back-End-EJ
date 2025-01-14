
package com.emakers.api_back.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(
                    "/v1/api/get-token",
                                "/swagger-ui.html",
                                "/swagger-ui/*",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**").permitAll()
                        .requestMatchers("/swagger-ui.html","/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()  // Permite acesso ao Swagger
                        .requestMatchers(HttpMethod.GET, "/auth/current").hasAnyAuthority("admin","user")
                        .requestMatchers(HttpMethod.POST, "/pessoas/{idPessoa}/emprestar/{idLivro}").hasAuthority("admin")
                        .requestMatchers(HttpMethod.POST, "/pessoas/{idPessoa}/devolver/{idLivro}").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET, "/pessoas/listar").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET, "/pessoas/{idPessoa}").hasAuthority("admin")
                        .requestMatchers(HttpMethod.POST, "/pessoas/registrar").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT, "/pessoas/atualizar/{idPessoa}").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/deletar/{idPessoa}").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET, "/livros/listar").hasAnyAuthority("admin","user")
                        .requestMatchers(HttpMethod.GET, "/livros/{idLivro}").hasAnyAuthority("admin","user")
                        .requestMatchers(HttpMethod.POST, "/livros/registrar").hasAnyAuthority("admin")
                        .requestMatchers(HttpMethod.POST, "/livros/atualizar/{idLivro}").hasAnyAuthority("admin")
                        .requestMatchers(HttpMethod.POST, "/livros/deletar/{idLivro}").hasAnyAuthority("admin")
                        .requestMatchers(HttpMethod.POST, "/livros/registrar/{isbn}").hasAnyAuthority("admin")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}