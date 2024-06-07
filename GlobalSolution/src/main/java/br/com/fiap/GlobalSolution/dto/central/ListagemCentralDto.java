package br.com.fiap.GlobalSolution.dto.central;

import br.com.fiap.GlobalSolution.model.Central;

public record ListagemCentralDto(Long id, String nome, String cnpj) {
    public ListagemCentralDto(Central central){
        this(central.getId(), central.getNome(), central.getCnpj());
    }
}
