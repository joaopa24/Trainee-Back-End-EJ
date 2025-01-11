package com.emakers.api_back.data.dto.response;

import com.emakers.api_back.data.entity.Pessoa;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record PessoaResponseDTO(
    Long idPessoa,
    String nome,
    String cep,
    String email,
    List<EmprestimoResponseDTO> emprestimos
) {
    public PessoaResponseDTO(Pessoa pessoa) {
        this(
            pessoa.getIdPessoa(),
            pessoa.getNome(),
            pessoa.getCep(),
            pessoa.getEmail(),
            pessoa.getEmprestimos() != null && !pessoa.getEmprestimos().isEmpty() 
                ? pessoa.getEmprestimos().stream()
                    .map(EmprestimoResponseDTO::new)
                    .collect(Collectors.toList())
                : Collections.emptyList()  // Retorna uma lista vazia caso não haja empréstimos
        );
    }
}
