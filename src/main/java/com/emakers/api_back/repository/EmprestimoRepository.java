package com.emakers.api_back.repository;

import com.emakers.api_back.data.entity.Emprestimo;

import feign.Param;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    @Query("SELECT e FROM Emprestimo e WHERE e.pessoa.idPessoa = :pessoaId AND e.livro.idLivro = :livroId AND e.dataDevolucao IS NULL")
    Optional<Emprestimo> findByPessoaIdPessoaAndLivroIdAndDataDevolucaoIsNull(
        @Param("pessoaId") Long pessoaId, 
        @Param("livroId") Long livroId);
}
