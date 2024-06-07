package br.com.fiap.GlobalSolution.dto.lixo;

import br.com.fiap.GlobalSolution.dto.robo.DetalhesRoboDto;
import br.com.fiap.GlobalSolution.model.Lixo;
import br.com.fiap.GlobalSolution.model.Tipo;

import java.time.LocalDateTime;

public record DetalhesLixoDto(Long id, String latitude, String longitude, Tipo tipo, LocalDateTime dataCaptura, DetalhesRoboDto roboDto) {
    public DetalhesLixoDto(Lixo lixo){
        this(lixo.getId(), lixo.getLatitude(), lixo.getLongitude(), lixo.getTipo(), lixo.getDataCaptura(), new DetalhesRoboDto(lixo.getRobo()));
    }
}
