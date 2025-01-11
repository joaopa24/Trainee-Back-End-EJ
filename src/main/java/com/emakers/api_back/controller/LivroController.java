package com.emakers.api_back.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.api_back.data.dto.request.LivroRequestDTO;
import com.emakers.api_back.data.dto.response.LivroResponseDTO;
import com.emakers.api_back.service.LivroService;
import com.emakers.api_back.api.BadRequestException;  // Importando a exceção personalizada

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @Operation(summary = "Encontrar livro por ID", description = "Retorna os detalhes de um livro a partir do seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso",
                     content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", 
                                                                          schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LivroResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @GetMapping("/{idLivro}")
    public ResponseEntity<LivroResponseDTO> encontrarPorId(@Parameter(description = "ID do livro a ser encontrado") @PathVariable Long idLivro) {
        LivroResponseDTO livro = livroService.encontrarPorId(idLivro);
        if (livro == null) {
            throw new BadRequestException("Livro não encontrado");
        }
        return ResponseEntity.ok().body(livro);
    }

    @Operation(summary = "Listar todos os livros", description = "Retorna a lista de todos os livros cadastrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de livros encontrada com sucesso",
                     content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", 
                                                                          schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LivroResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping(value = "/listar")
    public ResponseEntity<List<LivroResponseDTO>> encontrarTodos() {
        List<LivroResponseDTO> livros = livroService.listarTodos();
        if (livros.isEmpty()) {
            throw new BadRequestException("Nenhum livro encontrado");
        }
        return ResponseEntity.ok().body(livros);
    }

    @Operation(summary = "Registrar novo livro", description = "Cadastra um novo livro no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro registrado com sucesso",
                     content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", 
                                                                          schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LivroResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para o livro")
    })
    @PostMapping("/registrar")
    public ResponseEntity<LivroResponseDTO> registrar(@RequestBody LivroRequestDTO livroRequestDTO) {
        if (livroRequestDTO == null || livroRequestDTO.getTitulo().isEmpty()) {
            throw new BadRequestException("Dados inválidos para o livro");
        }
        LivroResponseDTO livro = livroService.registrar(livroRequestDTO);
        return ResponseEntity.ok().body(livro);
    }

    @Operation(summary = "Atualizar livro", description = "Atualiza os detalhes de um livro existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso",
                     content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", 
                                                                          schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LivroResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PutMapping("/atualizar/{idLivro}")
    public ResponseEntity<LivroResponseDTO> atualizar(
        @Parameter(description = "ID do livro a ser atualizado") @PathVariable Long idLivro,
        @RequestBody LivroRequestDTO livroRequestDTO
    ) {
        LivroResponseDTO livro = livroService.atualizar(idLivro, livroRequestDTO);
        if (livro == null) {
            throw new BadRequestException("Livro não encontrado para atualização");
        }
        return ResponseEntity.ok().body(livro);
    }

    @Operation(summary = "Deletar livro", description = "Remove um livro do sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @DeleteMapping("/deletar/{idLivro}")
    public ResponseEntity<String> deletar(@Parameter(description = "ID do livro a ser deletado") @PathVariable Long idLivro) {
        boolean deleted = livroService.deletar(idLivro);
        if (!deleted) {
            throw new BadRequestException("Livro não encontrado para deletar");
        }
        return ResponseEntity.ok().body("Livro deletado com sucesso");
    }

    @Operation(summary = "Registrar livro por ISBN", description = "Registra um livro no sistema a partir de seu ISBN.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro registrado com sucesso",
                     content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", 
                                                                          schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LivroResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "ISBN inválido ou erro ao registrar livro")
    })
    @PostMapping("/registrar/{isbn}")
    public ResponseEntity<LivroResponseDTO> registrarPorIsbn(@Parameter(description = "ISBN do livro a ser registrado") @PathVariable String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            throw new BadRequestException("ISBN inválido");
        }
        LivroResponseDTO livro = livroService.registrarPorIsbn(isbn);
        return ResponseEntity.ok().body(livro);
    }
}
