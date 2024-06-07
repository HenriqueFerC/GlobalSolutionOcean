package br.com.fiap.GlobalSolution.dto.central;

import br.com.fiap.GlobalSolution.model.Central;

public record DetalhesCentralDto(Long id, String nome, String cnpj) {
    public DetalhesCentralDto(Central central){
        this(central.getId(), central.getNome(), central.getCnpj());
    }
}
