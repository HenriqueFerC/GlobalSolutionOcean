package br.com.fiap.GlobalSolution.dto.funcionario;

import br.com.fiap.GlobalSolution.dto.coleta.DetalhesColetaDto;
import br.com.fiap.GlobalSolution.model.Funcionario;

public record ListagemFuncionarioDto(Long id, String nome, String cpf, int idade, String cargo) {
    public ListagemFuncionarioDto(Funcionario funcionario){
        this(funcionario.getId(), funcionario.getNome(), funcionario.getCpf(), funcionario.getIdade(), funcionario.getCargo());
    }
}
