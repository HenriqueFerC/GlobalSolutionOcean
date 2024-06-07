package br.com.fiap.GlobalSolution.model;

import br.com.fiap.GlobalSolution.dto.lixo.AtualizarLixoDto;
import br.com.fiap.GlobalSolution.dto.lixo.CadastrarLixoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor

@Entity
@Table(name = "TB_LIXO")
@EntityListeners(AuditingEntityListener.class)
public class Lixo {

    @Id
    @GeneratedValue
    @Column(name = "ID_LIXO")
    private Long id;

    @Column(name = "LATITUDE", length = 20, nullable = false)
    private String latitude;

    @Column(name = "LONGITUDE", length = 20, nullable = false)
    private String longitude;

    @Column(name = "TIPO", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(name = "DATA_DE_CAPTURA", nullable = false)
    @CreatedDate
    private LocalDateTime dataCaptura;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ROBO", nullable = false)
    private Robo robo;

    public Lixo(CadastrarLixoDto lixoDto, Robo robo){
        latitude = lixoDto.latitude();
        longitude = lixoDto.longitude();
        tipo = lixoDto.tipo();
        this.robo = robo;
    }

    public void atualizarLixo(AtualizarLixoDto lixoDto){
        latitude = lixoDto.latitude();
        longitude = lixoDto.longitude();
        tipo = lixoDto.tipo();
    }

}
