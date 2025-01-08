package com.emakers.api_back.service;

import com.emakers.api_back.data.entity.Pessoa;
import com.emakers.api_back.repository.PessoaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class AuthorizationService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Carregar usuário pelo email (necessário para o Spring Security)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Pessoa pessoa = pessoaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return pessoa;  // A entidade Pessoa já implementa UserDetails
    }

    // Lógica para registrar um novo usuário
    public Pessoa register(Pessoa pessoa) {
        if (pessoaRepository.findByEmail(pessoa.getEmail()).isPresent()) {
            throw new RuntimeException("Usuário já existe");
        }

        // Criptografar senha com BCryptPasswordEncoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(pessoa.getPassword());
        pessoa.setPassword(hashedPassword);  // Salvar senha criptografada
        return pessoaRepository.save(pessoa);
    }

    public Authentication authenticate(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Email e senha não podem ser nulos ou vazios");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }


}
