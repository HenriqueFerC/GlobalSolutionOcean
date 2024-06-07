package br.com.fiap.GlobalSolution.dto.funcionario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastrarFuncionarioDto(@NotBlank(message = "Nome não pode estar em branco!")
                                      @Size(max = 50, message = "Nome pode ter no máximo 50 caracteres!")
                                      String nome,
                                      @NotBlank(message = "CPF não pode estar em branco!")
                                      @Size(min = 14, max = 14, message = "CPF precisa ter 14 caracteres!")
                                      String cpf,
                                      @NotNull(message = "Idade não pode ser nulo!")
                                      @Size(max = 3, message = "Idade pode ter no máximo 3 casas de unidades!")
                                      int idade,
                                      @NotBlank(message = "Cargo não pode estar em branco!")
                                      @Size(max = 20, message = "Cargo pode ter no máximo 20 caracteres!")
                                      String cargo) {
}
