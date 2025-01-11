package com.emakers.api_back.service;

import com.emakers.api_back.data.dto.request.PessoaRequestDTO;
import com.emakers.api_back.data.dto.response.PessoaResponseDTO;
import com.emakers.api_back.data.entity.Pessoa;
import com.emakers.api_back.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    final PessoaRepository pessoaRepository;

    // Injeção de dependência pelo construtor
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    // Método para encontrar uma pessoa por ID
    public PessoaResponseDTO encontrarPorId(Long idPessoa) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(idPessoa);
        if (pessoa.isPresent()) {
            return new PessoaResponseDTO(pessoa.get());
        } else {
            throw new RuntimeException("Pessoa não encontrada para o ID: " + idPessoa);
        }
    }

    // Método para listar todas as pessoas
    public List<PessoaResponseDTO> listarTodos() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoas.stream()
                .map(PessoaResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Método para registrar uma nova pessoa
    public PessoaResponseDTO registrar(PessoaRequestDTO pessoaRequestDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaRequestDTO.nome());
        pessoa.setCep(pessoaRequestDTO.cep());
        pessoa.setEmail(pessoaRequestDTO.email());

        pessoa.setRole("user");
        pessoa.setPassword(pessoaRequestDTO.password());

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        return new PessoaResponseDTO(pessoaSalva);
    }

    // Método para atualizar uma pessoa existente
    public PessoaResponseDTO atualizar(Long idPessoa, PessoaRequestDTO pessoaRequestDTO) {
    Optional<Pessoa> pessoaExistente = pessoaRepository.findById(idPessoa);
    if (pessoaExistente.isPresent()) {
        Pessoa pessoa = pessoaExistente.get();
        pessoa.setNome(pessoaRequestDTO.nome());
        pessoa.setCep(pessoaRequestDTO.cep());
        pessoa.setEmail(pessoaRequestDTO.email());

        // Verificar se a senha foi fornecida, se sim, atualizar a senha
        if (pessoaRequestDTO.password() != null && !pessoaRequestDTO.password().isEmpty()) {
            pessoa.setPassword(pessoaRequestDTO.password());
        }

        Pessoa pessoaAtualizada = pessoaRepository.save(pessoa);
        return new PessoaResponseDTO(pessoaAtualizada);
    } else {
        throw new RuntimeException("Pessoa não encontrada para o ID: " + idPessoa);
    }
   }


    // Método para deletar uma pessoa
    public boolean deletar(Long idPessoa) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(idPessoa);
        if (pessoa.isPresent()) {
            pessoaRepository.deleteById(idPessoa);
            return true;
        } else {
            throw new RuntimeException("Pessoa não encontrada para o ID: " + idPessoa);
        }
    }
}
