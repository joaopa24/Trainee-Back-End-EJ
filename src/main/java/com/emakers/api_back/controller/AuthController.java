package com.emakers.api_back.controller;

import com.emakers.api_back.data.entity.Pessoa;
import com.emakers.api_back.service.AuthorizationService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorizationService authService;

    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
    
            if (email == null || password == null) {
                return ResponseEntity.badRequest().body("Email ou senha ausentes");
            }
    
            Authentication authentication = authService.authenticate(email, password);
            return ResponseEntity.ok("Login realizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha no login");
        }
    }
    

    // Endpoint para registro de um novo usuário
    @PostMapping("/register")
    public Pessoa register(@RequestBody Pessoa pessoa) {
        return authService.register(pessoa);
    }

    // Endpoint para verificar se o usuário está autenticado
    @GetMapping("/current")
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Usuário atual: " + authentication.getName();
    }
}
