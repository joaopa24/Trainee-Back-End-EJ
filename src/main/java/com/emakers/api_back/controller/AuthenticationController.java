package com.emakers.api_back.controller;

import com.emakers.api_back.auth.TokenService;
import com.emakers.api_back.data.dto.AuthenticationDTO;
import com.emakers.api_back.data.dto.response.LoginResponseDTO;
import com.emakers.api_back.data.dto.response.PessoaResponseDTO;
import com.emakers.api_back.data.entity.Pessoa;
import com.emakers.api_back.repository.PessoaRepository;

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

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private PessoaRepository repository;

     @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Pessoa) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    // Endpoint para registro
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid Pessoa data) {
        // Verificar se o usuário já existe
        if (repository.findByEmail(data.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }

        // Criptografar a senha
        System.out.println(data.getPassword());
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        System.out.println(encryptedPassword);
        // Criar novo usuário com a senha criptografada
        Pessoa newUser = new Pessoa();
        newUser.setEmail(data.getEmail());
        newUser.setNome(data.getNome());
        newUser.setCep(data.getCep());
        newUser.setPassword(encryptedPassword);

        // Se você estiver usando roles, atribua uma role padrão
        newUser.setRole("user");  // Adapte conforme necessário

        // Salvar o novo usuário no repositório
        repository.save(newUser);

        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    // Endpoint para verificar se o usuário está autenticado
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // Verifica se a autenticação está presente
    if (authentication == null || !authentication.isAuthenticated()) {
        return new ResponseEntity<>("Usuário não autenticado", HttpStatus.UNAUTHORIZED);
    }

    // Recupera o usuário autenticado (Pessoa)
    Pessoa currentUser = (Pessoa) authentication.getPrincipal();

    // Verifica se o objeto é uma instância de Pessoa
    if (currentUser instanceof Pessoa) {
        // Converte a entidade Pessoa para PessoaResponseDTO
        PessoaResponseDTO responseDTO = new PessoaResponseDTO(currentUser);

        // Retorna o DTO como resposta
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    } else {
        return new ResponseEntity<>("Erro ao recuperar o usuário", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}
