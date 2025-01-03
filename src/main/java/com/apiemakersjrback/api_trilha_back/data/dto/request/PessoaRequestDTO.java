package com.apiemakersjrback.api_trilha_back.data.dto.request;

import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public record PessoaRequestDTO(

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 80, message = "O nome não pode ter mais de 80 caracteres")
    String nome,

    @NotBlank(message = "O CEP é obrigatório")
    @Size(max = 9, message = "O CEP deve ter no máximo 9 caracteres")
    String cep,

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    @Size(max = 80, message = "O email não pode ter mais de 80 caracteres")
    String email,

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres")
    String password,

    List<Long> emprestimos
) {

    // Método para encriptar a senha usando BCrypt
    public PessoaRequestDTO {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        password = passwordEncoder.encode(password);
    }
}
