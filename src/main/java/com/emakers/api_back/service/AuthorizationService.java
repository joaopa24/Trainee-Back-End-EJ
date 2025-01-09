package com.emakers.api_back.service;

import com.emakers.api_back.data.dto.AuthenticationDTO;
import com.emakers.api_back.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    PessoaRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Certifique-se de que o PasswordEncoder está sendo injetado corretamente

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }

    public boolean authenticate(AuthenticationDTO data) {
    UserDetails user = repository.findByEmail(data.email());
    System.out.println(user);
        if (user == null) {
            return false; 
        }

        // Verificar se a senha fornecida corresponde à senha armazenada
        System.out.println(data.password());
        System.out.println("-----------");
        System.out.println(user.getPassword());
        if (!passwordEncoder.matches(data.password(), user.getPassword())) {
            System.out.println("DEUUUUUUUUU ERRRADOOOOOOOOO");
            return false;  // Senha inválida
        }

        UsernamePasswordAuthenticationToken authenticationToken = 
        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        System.out.println(authenticationToken);
        // Atualizar o SecurityContextHolder com o objeto de autenticação
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        
        // Aqui você pode fazer o processo de autenticação, se necessário
        return true;  // Autenticação bem-sucedida
    }

}
