package com.emakers.api_back.data.dto.response;

import com.emakers.api_back.data.entity.Livro;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public record LivroResponseDTO(
    Long idLivro,
    String nome,
    String autor,
    Date data,
    Boolean situacao,
    List<EmprestimoResponseDTO> emprestimos
) {
    public LivroResponseDTO(Livro livro) {
        this(
            livro.getIdLivro(),
            livro.getNome(),
            livro.getAutor(),
            livro.getData(),
            livro.getSituacao(),
            livro.getEmprestimos().stream()
                .map(EmprestimoResponseDTO::new) // Mapeando os empréstimos para o DTO
                .collect(Collectors.toList())
        );
    }
}
