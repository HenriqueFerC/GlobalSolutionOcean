package br.com.fiap.GlobalSolution.model;

import br.com.fiap.GlobalSolution.dto.coleta.AtualizarColetaDto;
import br.com.fiap.GlobalSolution.dto.coleta.CadastrarColetaDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor


@Entity
@Table(name = "TB_COLETA")
public class Coleta {

    @Id
    @GeneratedValue
    @Column(name = "ID_COLETA", nullable = false)
    private Long id;

    @Column(name = "QUANTIDADE_COLETADA", nullable = false)
    private Long quantidadeColetada;

    @Column(name = "DATA_COLETADA", nullable = false)
    private LocalDate dataColetada;

    @ManyToOne
    @JoinColumn(name = "ID_CENTRAL", nullable = false)
    private Central central;

    @OneToMany(mappedBy = "coleta", cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios;

    public Coleta(CadastrarColetaDto coletaDto, Central central){
        quantidadeColetada = coletaDto.quantidadeColetada();
        dataColetada = coletaDto.dataColetada();
        this.central = central;
    }

    public void atualizarColeta(AtualizarColetaDto coletaDto){
        quantidadeColetada = coletaDto.quantidadeColetada();
        dataColetada = coletaDto.dataColetada();
    }

    public void adicionarFuncionario(Funcionario funcionario){
        funcionarios.add(funcionario);
    }

    public void removerFuncionario(Funcionario funcionario){
        funcionarios.remove(funcionario);
    }


}
