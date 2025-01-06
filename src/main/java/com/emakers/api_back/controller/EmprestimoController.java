package com.emakers.api_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.api_back.data.entity.Emprestimo;
import com.emakers.api_back.service.EmprestimoService;  // Certifique-se de importar EmprestimoService

@RestController
@RequestMapping("/pessoas")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;  // Injeção do EmprestimoService

    @PostMapping("/{idPessoa}/emprestar/{idLivro}")
    public ResponseEntity<Emprestimo> pegarLivroEmprestado(
        @PathVariable Long idPessoa, @PathVariable Long idLivro) {
        Emprestimo emprestimo = emprestimoService.pegarEmprestado(idPessoa, idLivro); // Chama o serviço
        return ResponseEntity.ok(emprestimo);
    }

    @PostMapping("/{idPessoa}/devolver/{idLivro}")
    public ResponseEntity<Emprestimo> devolverLivro(
        @PathVariable Long idPessoa, @PathVariable Long idLivro) {
        Emprestimo emprestimo = emprestimoService.devolverLivro(idPessoa, idLivro);
        return ResponseEntity.ok(emprestimo);
    }
}
