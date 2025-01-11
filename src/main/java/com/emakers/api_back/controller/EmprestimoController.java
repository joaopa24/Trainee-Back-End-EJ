package com.emakers.api_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.api_back.data.dto.response.EmprestimoResponseDTO;
import com.emakers.api_back.service.EmprestimoService;
import com.emakers.api_back.api.BadRequestException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/pessoas")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @Operation(summary = "Pegar livro emprestado", description = "Permite que uma pessoa pegue um livro emprestado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro emprestado com sucesso", 
                     content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", 
                                                                          schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Erro na solicitação, por exemplo, livro já emprestado ou dados inválidos")
    })
    @PostMapping("/{idPessoa}/emprestar/{idLivro}")
    public ResponseEntity<EmprestimoResponseDTO> pegarLivroEmprestado(
        @Parameter(description = "ID da pessoa que está pegando o livro emprestado") @PathVariable Long idPessoa, 
        @Parameter(description = "ID do livro que está sendo emprestado") @PathVariable Long idLivro) {
        try {
            EmprestimoResponseDTO emprestimo = emprestimoService.pegarEmprestado(idPessoa, idLivro); // Chama o serviço
            return ResponseEntity.ok(emprestimo);
        } catch (BadRequestException ex) {
            throw new BadRequestException("Erro ao pegar o livro emprestado: " + ex.getMessage());
        } catch (Exception ex) {
            throw new BadRequestException("Erro inesperado ao pegar o livro emprestado.");
        }
    }

    @Operation(summary = "Devolver livro", description = "Permite que uma pessoa devolva um livro emprestado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro devolvido com sucesso", 
                     content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", 
                                                                          schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Erro na solicitação, por exemplo, livro não emprestado ou dados inválidos")
    })
    @PostMapping("/{idPessoa}/devolver/{idLivro}")
    public ResponseEntity<EmprestimoResponseDTO> devolverLivro(
        @Parameter(description = "ID da pessoa que está devolvendo o livro") @PathVariable Long idPessoa, 
        @Parameter(description = "ID do livro que está sendo devolvido") @PathVariable Long idLivro) {
        try {
            EmprestimoResponseDTO emprestimo = emprestimoService.devolverLivro(idPessoa, idLivro);
            return ResponseEntity.ok(emprestimo);
        } catch (BadRequestException ex) {
            throw new BadRequestException("Erro ao devolver o livro: " + ex.getMessage());
        } catch (Exception ex) {
            throw new BadRequestException("Erro inesperado ao devolver o livro.");
        }
    }
}
