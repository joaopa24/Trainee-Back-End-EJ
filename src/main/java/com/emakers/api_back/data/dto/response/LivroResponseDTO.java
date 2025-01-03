package com.emakers.api_back.data.dto.response;

import com.emakers.api_back.data.entity.Emprestimo;
import com.emakers.api_back.data.entity.Livro;
import java.util.List;

public record LivroResponseDTO(

    Long idLivro,
    String nome,
    String autor,
    java.sql.Date data,
    boolean situacao,
    List<Emprestimo> emprestimos

) {
    public LivroResponseDTO(Livro livro) {
        this(
            livro.getIdLivro(),
            livro.getNome(),
            livro.getAutor(),
            livro.getData(),
            livro.isSituacao(),
            livro.getEmprestimos()
        );
    }
}
