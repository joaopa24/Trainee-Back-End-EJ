package com.emakers.api_back.data.dto.response;

import com.emakers.api_back.data.entity.Emprestimo;
import java.util.List;

public record PessoaResponseDTO(

    Long idPessoa,
    String nome,
    String cep,
    String email,
    List<Emprestimo> emprestimos

) {
    public PessoaResponseDTO(com.emakers.api_back.data.entity.Pessoa pessoa) {
        this(
            pessoa.getIdPessoa(),
            pessoa.getNome(),
            pessoa.getCep(),
            pessoa.getEmail(),
            pessoa.getEmprestimos()
        );
    }
}
