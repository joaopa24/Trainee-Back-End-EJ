package com.emakers.api_back.repository;

import com.emakers.api_back.data.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    // Método para encontrar uma pessoa pelo email
    Optional<Pessoa> findByEmail(String email);

    // Método para verificar se uma pessoa já existe pelo email
    boolean existsByEmail(String email);

    // Método para encontrar pessoas pelo nome (busca parcial)
    List<Pessoa> findByNomeContaining(String nome);
}
