package br.com.fiap.GlobalSolution.model;

import br.com.fiap.GlobalSolution.dto.funcionario.AtualizarFuncionarioDto;
import br.com.fiap.GlobalSolution.dto.funcionario.CadastrarFuncionarioDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "TB_FUNCIONARIO")
public class Funcionario {
    @Id
    @GeneratedValue
    @Column(name = "ID_FUNCIONARIO", nullable = false)
    private Long id;

    @Column(name = "NOME", length = 50, nullable = false)
    private String nome;

    @Column(name = "CPF", length = 14, nullable = false)
    private String cpf;

    @Column(name = "IDADE", nullable = false)
    private int idade;

    @Column(name = "CARGO", length = 20, nullable = false)
    private String cargo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_COLETA", nullable = false)
    private Coleta coleta;

    public Funcionario(CadastrarFuncionarioDto funcionarioDto, Coleta coleta){
        nome = funcionarioDto.nome();
        cpf = funcionarioDto.cpf();
        idade = funcionarioDto.idade();
        cargo = funcionarioDto.cargo();
        this.coleta = coleta;
    }

    public void atualizarFuncionario(AtualizarFuncionarioDto funcionarioDto){
        nome = funcionarioDto.nome();
        cpf = funcionarioDto.cpf();
        idade = funcionarioDto.idade();
        cargo = funcionarioDto.cargo();
    }

}
