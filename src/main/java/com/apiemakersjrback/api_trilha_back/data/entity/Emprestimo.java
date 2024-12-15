package com.apiemakersjrback.api_trilha_back.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long idEmprestimo;

    // Relacionamento com Livro (muitos-para-um)
    @ManyToOne
    @JoinColumn(name = "idLivro", nullable = false)
    private Livro livro;

    // Relacionamento com Pessoa (muitos-para-um)
    @ManyToOne
    @JoinColumn(name = "idPessoa", nullable = false)
    private Pessoa pessoa;
}
