package br.com.fiap.GlobalSolution.dto.funcionario;

import br.com.fiap.GlobalSolution.dto.coleta.DetalhesColetaDto;
import br.com.fiap.GlobalSolution.model.Funcionario;

public record DetalhesFuncionarioDto(Long id, String nome, String cpf, int idade, String cargo, DetalhesColetaDto coletaDto) {
    public DetalhesFuncionarioDto(Funcionario funcionario){
        this(funcionario.getId(), funcionario.getNome(), funcionario.getCpf(), funcionario.getIdade(), funcionario.getCargo(), new DetalhesColetaDto(funcionario.getColeta()));
    }
}
