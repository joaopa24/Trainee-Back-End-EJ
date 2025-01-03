package com.apiemakersjrback.api_trilha_back.repository;

import com.apiemakersjrback.api_trilha_back.data.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Método para encontrar livros pelo nome (opcional)
    List<Livro> findByNomeContaining(String nome);

    // Método para encontrar livros por autor (opcional)
    List<Livro> findByAutorContaining(String autor);

    // Método para encontrar livros disponíveis (situacao == true)
    List<Livro> findBySituacaoTrue();

    // Método para encontrar livros não disponíveis (situacao == false)
    List<Livro> findBySituacaoFalse();
}
