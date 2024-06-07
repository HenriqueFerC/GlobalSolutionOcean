package br.com.fiap.GlobalSolution.model;

import br.com.fiap.GlobalSolution.dto.robo.AtualizarRoboDto;
import br.com.fiap.GlobalSolution.dto.robo.CadastrarRoboDto;
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
@Table(name = "TB_ROBO")
public class Robo {

    @Id
    @Column(name = "ID_ROBO", nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "NOME", length = 50, nullable = false)
    private String nome;

    @Column(name = "STATUS", length = 7, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "ULTIMA_MANUTENCAO", nullable = false)
    private LocalDate ultimaManutencao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CENTRAL")
    private Central central;

    @OneToMany(mappedBy = "robo", cascade = CascadeType.ALL)
    private List<Lixo> lixos;

    public Robo(CadastrarRoboDto roboDto, Central central){
        nome = roboDto.nome();
        status = roboDto.status();
        ultimaManutencao = roboDto.ultimaManutencao();
        this.central = central;
    }

    public void adicionarLixo(Lixo lixo){
        lixos.add(lixo);
    }

    public void removerLixo(Lixo lixo){
        lixos.remove(lixo);
    }

    public void atualizarRobo(AtualizarRoboDto roboDto){
        nome = roboDto.nome();
        status = roboDto.status();
        ultimaManutencao = roboDto.ultimaManutencao();
    }

}
