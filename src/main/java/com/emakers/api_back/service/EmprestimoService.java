package com.emakers.api_back.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emakers.api_back.data.dto.response.EmprestimoResponseDTO;
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

    public EmprestimoResponseDTO pegarEmprestado(Long idPessoa, Long idLivro) {
        // Busca a pessoa pelo ID
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
            .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        // Busca o livro pelo ID
        Livro livro = livroRepository.findById(idLivro)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        // Verifica se o livro está disponível para empréstimo
        if (!livro.isDisponivel()) {
            throw new RuntimeException("Livro não está disponível para empréstimo");
        }

        // Verifica quantos livros a pessoa já tem emprestado e não devolvido
        int emprestimosAtivos = emprestimoRepository.countByPessoaAndDataDevolucaoIsNull(pessoa);

        // Lança exceção se a pessoa já tiver 3 livros não devolvidos
        if (emprestimosAtivos >= 3) {
            throw new RuntimeException("Limite de 3 livros emprestados não devolvidos alcançado.");
        }

        // Cria um novo empréstimo
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setPessoa(pessoa);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());

        // Marca o livro como indisponível
        livro.setDisponivel(false);
        livroRepository.save(livro);

        // Salva o empréstimo e retorna o DTO
        Emprestimo emprestimoSalvo = emprestimoRepository.save(emprestimo);
        return new EmprestimoResponseDTO(emprestimoSalvo);
    }

    public EmprestimoResponseDTO devolverLivro(Long idPessoa, Long idLivro) {
        Emprestimo emprestimo = emprestimoRepository
            .findByPessoaIdPessoaAndLivroIdAndDataDevolucaoIsNull(idPessoa, idLivro)
            .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado ou já devolvido"));

        // Marca a data de devolução
        emprestimo.setDataDevolucao(LocalDate.now());

        // Marca o livro como disponível
        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroRepository.save(livro);

        // Salva a devolução e retorna o DTO
        Emprestimo emprestimoAtualizado = emprestimoRepository.save(emprestimo);
        return new EmprestimoResponseDTO(emprestimoAtualizado);
    }
}

