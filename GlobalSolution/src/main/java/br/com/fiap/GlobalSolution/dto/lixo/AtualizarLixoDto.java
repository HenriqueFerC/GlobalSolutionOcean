package br.com.fiap.GlobalSolution.dto.lixo;

import br.com.fiap.GlobalSolution.model.Tipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AtualizarLixoDto(@NotBlank(message = "Latitude não pode estar em branco!")
                               @Size(max = 20, message = "Latitude pode ter no maximo 20 caracteres!")
                               String latitude,
                               @NotBlank(message = "Longitude não pode estar em branco!")
                               @Size(max = 20, message = "Longitude pode ter no maximo 20 caracteres!")
                               String longitude,
                               @NotEmpty(message = "Tipo não pode ser vazio!")
                               Tipo tipo) {
}
