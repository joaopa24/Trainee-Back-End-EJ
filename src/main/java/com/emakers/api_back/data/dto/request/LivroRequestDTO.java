package com.emakers.api_back.data.dto.request;

import java.sql.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LivroRequestDTO (
    
    @NotBlank(message = "O nome do livro é obrigatório")
    @Size(max = 45, message = "O nome do livro não pode ter mais de 45 caracteres")
    String nome,
    
    @NotBlank(message = "O nome do autor é obrigatório")
    @Size(max = 45, message = "O nome do autor não pode ter mais de 45 caracteres")
    String autor,
    
    @NotNull(message = "A data de lançamento é obrigatória")
    Date data,
    
    boolean situacao
) {}
