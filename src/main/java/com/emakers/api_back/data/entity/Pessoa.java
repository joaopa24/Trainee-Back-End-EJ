package com.emakers.api_back.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "Pessoa")
public class Pessoa implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPessoa", nullable = false, unique = true)
    private long idPessoa;

    @Column(name = "nome", nullable = false, length = 80)
    private String nome;

    @Column(name = "cep", nullable = false, length = 9)
    private String cep;

    @Column(name = "email", nullable = false, length = 80, unique = true)
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "idPessoa"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<UserRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "pessoa", fetch = FetchType.EAGER)
    private List<Emprestimo> emprestimos;

    // Método para encriptar a senha usando BCrypt
    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    // Implementação da interface UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Aqui, os papéis são convertidos para SimpleGrantedAuthority, que são necessários para o Spring Security
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRole()))
                    .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email; // Aqui, você pode usar o login ou email como o identificador de usuário
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
