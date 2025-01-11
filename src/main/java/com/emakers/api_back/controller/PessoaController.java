package com.emakers.api_back.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emakers.api_back.data.dto.request.PessoaRequestDTO;
import com.emakers.api_back.data.dto.response.PessoaResponseDTO;
import com.emakers.api_back.service.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "Pessoa", description = "Endpoints para gerenciar pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    // Endpoint para buscar uma pessoa por ID
    @Operation(
        summary = "Buscar pessoa por ID",
        description = "Recupera uma pessoa com base no seu ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
        }
    )
    @GetMapping("/{idPessoa}")
    public ResponseEntity<PessoaResponseDTO> encontrarPorId(@Parameter(description = "ID da pessoa") @PathVariable Long idPessoa) {
        return ResponseEntity.ok().body(pessoaService.encontrarPorId(idPessoa));
    }

    // Endpoint para listar todas as pessoas
    @Operation(
        summary = "Listar todas as pessoas",
        description = "Recupera uma lista de todas as pessoas registradas",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de pessoas retornada")
        }
    )
    @GetMapping("/listar")
    public ResponseEntity<List<PessoaResponseDTO>> listarTodos() {
        return ResponseEntity.ok().body(pessoaService.listarTodos());
    }

    // Endpoint para registrar uma nova pessoa
    @Operation(
        summary = "Registrar nova pessoa",
        description = "Registra uma nova pessoa no sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Pessoa registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
        }
    )
    @PostMapping("/registrar")
    public ResponseEntity<PessoaResponseDTO> registrar(@RequestBody @Parameter(description = "Dados da nova pessoa") PessoaRequestDTO pessoaRequestDTO) {
        return ResponseEntity.ok().body(pessoaService.registrar(pessoaRequestDTO));
    }

    // Endpoint para atualizar uma pessoa existente
    @Operation(
        summary = "Atualizar pessoa existente",
        description = "Atualiza os dados de uma pessoa registrada",
        responses = {
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
        }
    )
    @PutMapping("/atualizar/{idPessoa}")
    public ResponseEntity<PessoaResponseDTO> atualizar(
        @Parameter(description = "ID da pessoa") @PathVariable Long idPessoa,
        @RequestBody @Parameter(description = "Dados para atualização da pessoa") PessoaRequestDTO pessoaRequestDTO
    ) {
        return ResponseEntity.ok().body(pessoaService.atualizar(idPessoa, pessoaRequestDTO));
    }

    // Endpoint para deletar uma pessoa
    @Operation(
        summary = "Deletar pessoa",
        description = "Deleta uma pessoa com base no seu ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Pessoa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
        }
    )
    @DeleteMapping("/deletar/{idPessoa}")
    public ResponseEntity<String> deletar(@Parameter(description = "ID da pessoa") @PathVariable Long idPessoa) {
        return ResponseEntity.ok().body(pessoaService.deletar(idPessoa));
    }
}
