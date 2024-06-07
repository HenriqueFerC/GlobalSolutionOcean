package br.com.fiap.GlobalSolution.dto.coleta;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CadastrarColetaDto(@NotNull(message = "Quantidade Coletada não pode ser nula!")
                                 Long quantidadeColetada,
                                 @NotEmpty(message = "Data de coleta é obrigatório!")
                                 LocalDate dataColetada) {
}
