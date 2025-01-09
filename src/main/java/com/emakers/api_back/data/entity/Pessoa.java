package com.emakers.api_back.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

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

    // Alterado para uma única String representando o papel
    @Column(name = "role", nullable = false)
    private String role; // Papel do usuário, como "ROLE_ADMIN", "ROLE_USER", etc.

    @OneToMany(mappedBy = "pessoa", fetch = FetchType.EAGER)
    private List<Emprestimo> emprestimos;

    // Método para encriptar a senha usando BCrypt
    public void setPassword(String password) {
        this.password = password;
    }

    // Implementação da interface UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Agora a autoridade é simplesmente o papel armazenado como String
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email; // Usamos o email como identificador único do usuário
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

    public String getRole() {
        return role;
    }
}
