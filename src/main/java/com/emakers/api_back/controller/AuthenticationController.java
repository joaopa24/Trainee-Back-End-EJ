package com.emakers.api_back.controller;

import com.emakers.api_back.auth.TokenService;
import com.emakers.api_back.data.dto.AuthenticationDTO;
import com.emakers.api_back.data.dto.response.LoginResponseDTO;
import com.emakers.api_back.data.dto.response.PessoaResponseDTO;
import com.emakers.api_back.data.entity.Pessoa;
import com.emakers.api_back.repository.PessoaRepository;
import com.emakers.api_back.api.BadRequestException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PessoaRepository repository;

    @Operation(summary = "Login do usuário", description = "Autentica o usuário e retorna um token JWT para acesso.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados de login inválidos")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((Pessoa) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception ex) {
            throw new BadRequestException("Dados de login inválidos");
        }
    }

    @Operation(summary = "Registro de novo usuário", description = "Cria um novo usuário no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid Pessoa data) {
        // Verificar se o usuário já existe
        if (repository.findByEmail(data.getEmail()) != null) {
            throw new BadRequestException("Usuário já existe");
        }

        // Criptografar a senha
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        Pessoa newUser = new Pessoa();
        newUser.setEmail(data.getEmail());
        newUser.setNome(data.getNome());
        newUser.setCep(data.getCep());
        newUser.setPassword(encryptedPassword);
        newUser.setRole("user");

        repository.save(newUser);

        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    @Operation(summary = "Verificar usuário autenticado", description = "Retorna os dados do usuário atualmente autenticado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário autenticado retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Usuário não autenticado")
    })
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadRequestException("Usuário não autenticado");
        }

        Pessoa currentUser = (Pessoa) authentication.getPrincipal();
        PessoaResponseDTO responseDTO = new PessoaResponseDTO(currentUser);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
