package br.com.fiap.GlobalSolution.dto.robo;

import br.com.fiap.GlobalSolution.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CadastrarRoboDto(@NotBlank(message = "Nome não pode estar em branco!")
                               @Size(max = 50, message = "Nome não pode ter mais que 50 caracteres!")
                               String nome,
                               @NotEmpty(message = "Status não pode ser vazio!")
                               Status status,
                               @NotEmpty(message = "Data não pode ser vazia!")
                               LocalDate ultimaManutencao) {
}
