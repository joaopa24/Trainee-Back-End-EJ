package com.emakers.api_back.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emakers.api_back.data.dto.request.PessoaRequestDTO;
import com.emakers.api_back.data.dto.response.PessoaResponseDTO;
import com.emakers.api_back.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    // Endpoint para buscar uma pessoa por ID
    @GetMapping("/{idPessoa}")
    public ResponseEntity<PessoaResponseDTO> encontrarPorId(@PathVariable Long idPessoa) {
        return ResponseEntity.ok().body(pessoaService.encontrarPorId(idPessoa));
    }

    // Endpoint para listar todas as pessoas
    @GetMapping("/listar")
    public ResponseEntity<List<PessoaResponseDTO>> listarTodos() {
        return ResponseEntity.ok().body(pessoaService.listarTodos());
    }

    // Endpoint para registrar uma nova pessoa
    @PostMapping("/registrar")
    public ResponseEntity<PessoaResponseDTO> registrar(@RequestBody PessoaRequestDTO pessoaRequestDTO) {
        return ResponseEntity.ok().body(pessoaService.registrar(pessoaRequestDTO));
    }

    // Endpoint para atualizar uma pessoa existente
    @PutMapping("/atualizar/{idPessoa}")
    public ResponseEntity<PessoaResponseDTO> atualizar(
        @PathVariable Long idPessoa,
        @RequestBody PessoaRequestDTO pessoaRequestDTO
    ) {
        return ResponseEntity.ok().body(pessoaService.atualizar(idPessoa, pessoaRequestDTO));
    }

    // Endpoint para deletar uma pessoa
    @DeleteMapping("/deletar/{idPessoa}")
    public ResponseEntity<String> deletar(@PathVariable Long idPessoa) {
        return ResponseEntity.ok().body(pessoaService.deletar(idPessoa));
    }
}

