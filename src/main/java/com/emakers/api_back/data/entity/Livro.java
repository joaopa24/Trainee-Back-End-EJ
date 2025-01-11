package com.emakers.api_back.data.entity;

import java.sql.Date;
import java.time.LocalDate;
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

    @Column(name = "nome",nullable = true, length = 45)
    private String nome;

    @Column(name = "autor",nullable = true, length = 45)
    private String autor;

    @Column(name = "data-de-lancamento",nullable = true, length = 45)
    private Date data;

    @Column(name = "situacao",nullable = true)
    private Boolean situacao;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "livro")
    private List<Emprestimo> emprestimos;


    public boolean isDisponivel() {
        return situacao;
    }

    public void setDate(Date data){
        this.data = data;
    }

    public void setDisponivel(boolean situacao) {
        this.situacao = situacao;
    }
}
