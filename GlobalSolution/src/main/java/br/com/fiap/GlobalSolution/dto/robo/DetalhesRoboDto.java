package br.com.fiap.GlobalSolution.dto.robo;

import br.com.fiap.GlobalSolution.dto.central.DetalhesCentralDto;
import br.com.fiap.GlobalSolution.model.Robo;
import br.com.fiap.GlobalSolution.model.Status;

import java.time.LocalDate;

public record DetalhesRoboDto(Long id, String nome, Status status, LocalDate ultimaManutencao, DetalhesCentralDto centralDto) {
    public DetalhesRoboDto(Robo robo){
        this(robo.getId(), robo.getNome(), robo.getStatus(), robo.getUltimaManutencao(), new DetalhesCentralDto(robo.getCentral()));
    }
}
