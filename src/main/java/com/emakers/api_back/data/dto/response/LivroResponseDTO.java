package com.emakers.api_back.data.dto.response;

import com.emakers.api_back.data.entity.Livro;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
            // Usando Optional para garantir que não vai lançar NullPointerException
            Optional.ofNullable(livro.getEmprestimos()) // Verifica se a lista de empréstimos é null
                .orElse(Collections.emptyList()) // Caso seja null, usa uma lista vazia
                .stream() // Faz o stream na lista de empréstimos
                .map(EmprestimoResponseDTO::new) // Mapeia os empréstimos para o DTO
                .collect(Collectors.toList()) // Coleta os resultados em uma lista
        );
    }
}
