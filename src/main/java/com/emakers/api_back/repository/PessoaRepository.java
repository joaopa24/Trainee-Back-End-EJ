package com.emakers.api_back.repository;

import com.emakers.api_back.data.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    UserDetails findByEmail(String email);
}
