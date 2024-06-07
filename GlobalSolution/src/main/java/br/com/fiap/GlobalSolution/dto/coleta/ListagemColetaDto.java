package br.com.fiap.GlobalSolution.dto.coleta;

import br.com.fiap.GlobalSolution.dto.central.DetalhesCentralDto;
import br.com.fiap.GlobalSolution.model.Coleta;

import java.time.LocalDate;

public record ListagemColetaDto(Long id, Long quantidadeColetada, LocalDate dataColetada) {
    public ListagemColetaDto(Coleta coleta){
        this(coleta.getId(), coleta.getQuantidadeColetada(), coleta.getDataColetada());
    }
}
