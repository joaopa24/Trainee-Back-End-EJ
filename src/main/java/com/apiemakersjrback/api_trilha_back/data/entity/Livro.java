package com.apiemakersjrback.api_trilha_back.data.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Livro")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long idLivro;

    @Column(name = "nome",nullable = false, length = 45)
    private String nome;

    @Column(name = "autor",nullable = false, length = 45)
    private String autor;

    @Column(name = "data-de-lancamento",nullable = false, length = 45)
    private Date date;

    @OneToMany(mappedBy = "livro")
    private List<Emprestimo> emprestimos;
}
