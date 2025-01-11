package com.emakers.api_back.data.dto.response;

import com.emakers.api_back.data.entity.Emprestimo;
import java.time.LocalDate;

public record EmprestimoResponseDTO(
    Long idEmprestimo,
    Long idLivro,
    LocalDate dataEmprestimo,
    LocalDate dataDevolucao
) {
    public EmprestimoResponseDTO(Emprestimo emprestimo) {
        this(
            emprestimo.getIdEmprestimo(),
            emprestimo.getLivro().getIdLivro(), // Pega o ID do livro associado
            emprestimo.getDataEmprestimo(),
            emprestimo.getDataDevolucao()
        );
    }

}
