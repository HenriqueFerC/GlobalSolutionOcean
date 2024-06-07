package br.com.fiap.GlobalSolution.model;

import br.com.fiap.GlobalSolution.dto.central.AtualizarCentralDto;
import br.com.fiap.GlobalSolution.dto.central.CadastrarCentralDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "TB_CENTRAL")
public class Central {

    @Id
    @Column(name = "ID_CENTRAL", nullable = false)
    @GeneratedValue
    private Long id;
    @Column(name = "NOME_CENTRAL", length = 50, nullable = false)
    private String nome;
    @Column(name = "CNPJ", length = 14, nullable = false)
    private String cnpj;

    @OneToMany(mappedBy = "central", cascade = CascadeType.ALL)
    private List<Robo> robos;

    @OneToMany(mappedBy = "central", cascade = CascadeType.ALL)
    private List<Coleta> coletas;

    public Central(CadastrarCentralDto centralDto){
        nome = centralDto.nome();
        cnpj = centralDto.cnpj();
    }

    public void atualizarCentral(AtualizarCentralDto centralDto){
        nome = centralDto.nome();
        cnpj = centralDto.cnpj();
    }

    public void adicionarRobo(Robo robo){
        robos.add(robo);
    }

    public void removerRobo(Robo robo){
        robos.remove(robo);
    }

    public void adicionarColeta(Coleta coleta){
        coletas.add(coleta);
    }

    public void removerColeta(Coleta coleta){
        coletas.remove(coleta);
    }

}
