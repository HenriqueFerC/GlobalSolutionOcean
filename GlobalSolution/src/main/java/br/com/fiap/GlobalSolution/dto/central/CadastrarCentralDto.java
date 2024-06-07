package br.com.fiap.GlobalSolution.dto.central;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastrarCentralDto(@NotBlank(message = "Nome não pode estar em branco!")
                                  @Size(max = 50, message = "Nome não pode ser maior do que 50 caracteres!")
                                  String nome,
                                  @NotBlank(message = "CNPJ não pode estar em branco!")
                                  @Size(min = 14, max = 14, message = "CNPJ precisa ter 14 caracteres!")
                                  String cnpj) {
}
