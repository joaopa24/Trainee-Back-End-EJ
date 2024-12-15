package com.apiemakersjrback.api_trilha_back.data.entity;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@Entity
@Table(name = "Pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
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

    // Método para encriptar a senha usando BCrypt
    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    @OneToMany(mappedBy = "pessoa")
    private List<Emprestimo> emprestimos;
}
