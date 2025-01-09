package com.emakers.api_back.auth;

import com.emakers.api_back.repository.PessoaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    PessoaRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        System.out.println("cuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        System.out.println(token);
        System.out.println("--------------------------------");
        if(token != null){
            var login = tokenService.validateToken(token);
            System.out.println("sxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            System.out.println(login);
            System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooo");
            UserDetails user = repository.findByEmail(login);
            System.out.println(user);
            System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuy");
            
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
