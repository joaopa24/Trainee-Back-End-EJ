package com.emakers.api_back.service;

import com.emakers.api_back.data.dto.request.LivroRequestDTO;
import com.emakers.api_back.data.dto.response.LivroApiResponseDTO;
import com.emakers.api_back.data.dto.response.LivroResponseDTO;
import com.emakers.api_back.data.entity.Livro;
import com.emakers.api_back.repository.LivroRepository;

import jakarta.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {

    final LivroRepository livroRepository;

    final LivroApiService livroApiService;  // Adicionar o serviço da API

    public LivroService(LivroRepository livroRepository, LivroApiService livroApiService) {
        this.livroRepository = livroRepository;
        this.livroApiService = livroApiService;
    }

    // Método para encontrar um livro por ID
    public LivroResponseDTO encontrarPorId(Long idLivro) {
        Optional<Livro> livro = livroRepository.findById(idLivro);
        if (livro.isPresent()) {
            return new LivroResponseDTO(livro.get());
        } else {
            throw new RuntimeException("Livro não encontrado para o ID: " + idLivro);
        }
    }

    // Método para listar todos os livros
    @Transactional
    public List<LivroResponseDTO> listarTodos() {
        List<Livro> livros = livroRepository.findAll();

        // Inicializar a coleção emprestimos
        livros.forEach(livro -> Hibernate.initialize(livro.getEmprestimos()));

        return livros.stream()
            .map(LivroResponseDTO::new)
            .collect(Collectors.toList());
    }

    // Método para registrar um novo livro
    public LivroResponseDTO registrar(LivroRequestDTO livroRequestDTO) {
        Livro livro = new Livro();
        livro.setNome(livroRequestDTO.nome());
        livro.setAutor(livroRequestDTO.autor());
        livro.setData(livroRequestDTO.data());
        livro.setSituacao(livroRequestDTO.situacao());

        Livro livroSalvo = livroRepository.save(livro);
        return new LivroResponseDTO(livroSalvo);
    }

    // Método para atualizar um livro existente
    public LivroResponseDTO atualizar(Long idLivro, LivroRequestDTO livroRequestDTO) {
        Optional<Livro> livroExistente = livroRepository.findById(idLivro);
        if (livroExistente.isPresent()) {
            Livro livro = livroExistente.get();
            livro.setNome(livroRequestDTO.nome());
            livro.setAutor(livroRequestDTO.autor());
            livro.setData(livroRequestDTO.data());
            livro.setSituacao(livroRequestDTO.situacao());

            Livro livroAtualizado = livroRepository.save(livro);
            return new LivroResponseDTO(livroAtualizado);
        } else {
            throw new RuntimeException("Livro não encontrado para o ID: " + idLivro);
        }
    }

    // Método para deletar um livro
    public String deletar(Long idLivro) {
        Optional<Livro> livro = livroRepository.findById(idLivro);
        if (livro.isPresent()) {
            livroRepository.deleteById(idLivro);
            return "Livro deletado com sucesso!";
        } else {
            throw new RuntimeException("Livro não encontrado para o ID: " + idLivro);
        }
    }
    
    public LivroResponseDTO registrarPorIsbn(String isbn) {

        LivroApiResponseDTO livroApiResponse = livroApiService.buscarLivroPorIsbn(isbn);
    
        Livro livro = new Livro();
        livro.setNome(livroApiResponse.getTitle());
        livro.setAutor(String.join(", ", livroApiResponse.getAuthors()));
    
        int ano = livroApiResponse.getYear();
        String data = ano + "-01-01";
        System.out.println(data);
    
        try {
            livro.setData(java.sql.Date.valueOf(data));  // tenta converter a data
        } catch (IllegalArgumentException e) {
            // Se a data for inválida, define como null
            livro.setData(null);
        }
    
        livro.setSituacao(true); 
    
        Livro livroSalvo = livroRepository.save(livro);
    
        return new LivroResponseDTO(livroSalvo);
    }
    
}
