package br.com.fiap.GlobalSolution.dto.coleta;

import br.com.fiap.GlobalSolution.dto.central.DetalhesCentralDto;
import br.com.fiap.GlobalSolution.model.Central;
import br.com.fiap.GlobalSolution.model.Coleta;

import java.time.LocalDate;

public record DetalhesColetaDto(Long id, Long quantidadeColetada, LocalDate dataColetada, DetalhesCentralDto centralDto ) {
    public DetalhesColetaDto(Coleta coleta){
        this(coleta.getId(), coleta.getQuantidadeColetada(), coleta.getDataColetada(), new DetalhesCentralDto(coleta.getCentral()));
    }
}
