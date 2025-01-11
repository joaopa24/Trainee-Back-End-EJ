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
    private PasswordEncoder passwordEncoder;  
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

        System.out.println(data.password());
        System.out.println(user.getPassword());
        if (!passwordEncoder.matches(data.password(), user.getPassword())) {
            return false;  // Senha inválida
        }

        UsernamePasswordAuthenticationToken authenticationToken = 
        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        System.out.println(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        
        return true;  
    }

}
