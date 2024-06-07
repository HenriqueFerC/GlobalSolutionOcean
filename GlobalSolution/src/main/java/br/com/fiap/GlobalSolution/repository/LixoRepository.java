package br.com.fiap.GlobalSolution.repository;

import br.com.fiap.GlobalSolution.model.Lixo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface LixoRepository extends JpaRepository<Lixo, Long> {
    Page<Lixo> findByDataCapturaBetween(LocalDateTime dataCapturaInicial, LocalDateTime dataCapturaFinal, Pageable pageable);

    Page<Lixo> findByDataCapturaAfter(LocalDateTime dataInicial, Pageable pageable);

    Page<Lixo> findByDataCapturaBefore(LocalDateTime dataInicial, Pageable pageable);
}
