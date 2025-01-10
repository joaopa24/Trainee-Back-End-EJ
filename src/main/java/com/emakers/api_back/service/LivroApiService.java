package com.emakers.api_back.service;

import com.emakers.api_back.data.dto.response.LivroApiResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LivroApiService {

    private final RestTemplate restTemplate;

    public LivroApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LivroApiResponseDTO buscarLivroPorIsbn(String isbn) {
        String url = "https://brasilapi.com.br/api/isbn/v1/" + isbn;
        return restTemplate.getForObject(url, LivroApiResponseDTO.class);
    }
}
