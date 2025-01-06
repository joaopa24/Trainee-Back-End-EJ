package com.emakers.api_back.service;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.emakers.api_back.data.entity.Emprestimo;
import com.emakers.api_back.data.entity.Livro;
import com.emakers.api_back.data.entity.Pessoa;
import com.emakers.api_back.repository.EmprestimoRepository;
import com.emakers.api_back.repository.LivroRepository;
import com.emakers.api_back.repository.PessoaRepository;

@Service
public class EmprestimoService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public Emprestimo pegarEmprestado(Long idPessoa, Long idLivro) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
            .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        Livro livro = livroRepository.findById(idLivro)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        if (!livro.isDisponivel()) {
            throw new RuntimeException("Livro não está disponível para empréstimo");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setPessoa(pessoa);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());

        // Marca o livro como indisponível
        livro.setDisponivel(false);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo devolverLivro(Long idPessoa, Long idLivro) {
        Emprestimo emprestimo = emprestimoRepository.findByPessoaIdPessoaAndLivroIdAndDataDevolucaoIsNull(idPessoa, idLivro)
            .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado ou já devolvido"));

        // Marca a data de devolução
        emprestimo.setDataDevolucao(LocalDate.now());

        // Marca o livro como disponível
        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }
}
