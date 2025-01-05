package com.emakers.api_back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/{idLivro}")
    public ResponseEntity<LivroResponseDTO> encontrarPorId(@PathVariable Long idLivro) {
        return ResponseEntity.ok().body(livroService.encontrarPorId(idLivro));
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<List<LivroResponseDTO>> encontrarTodos() {
        return ResponseEntity.ok().body(livroService.listarTodos());
    }

    @PostMapping("/registrar")
    public ResponseEntity<LivroResponseDTO> registrar(@RequestBody LivroRequestDTO livroRequestDTO) {
        return ResponseEntity.ok().body(livroService.registrar(livroRequestDTO));
    }

    @PutMapping("/atualizar/{idLivro}")
    public ResponseEntity<LivroResponseDTO> atualizar(
        @PathVariable Long idLivro,
        @RequestBody LivroRequestDTO livroRequestDTO
    ) {
        return ResponseEntity.ok().body(livroService.atualizar(idLivro, livroRequestDTO));
    }

    @DeleteMapping("/deletar/{idLivro}")
    public ResponseEntity<String> deletar(@PathVariable Long idLivro) {
        return ResponseEntity.ok().body(livroService.deletar(idLivro));
    }
}
