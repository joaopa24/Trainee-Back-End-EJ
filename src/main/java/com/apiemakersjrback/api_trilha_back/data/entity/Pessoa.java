package com.apiemakersjrback.api_trilha_back.data.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long idPessoa;
    
    @Column(name = "nome",nullable = false, length = 80)
    private String nome;

    @Column(name = "cep",nullable = false, length = 9)
    private char cep;

    @OneToMany(mappedBy = "pessoa")
    private List<Emprestimo> emprestimos;
}
