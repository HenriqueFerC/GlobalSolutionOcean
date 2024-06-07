package br.com.fiap.GlobalSolution.repository;

import br.com.fiap.GlobalSolution.model.Robo;
import br.com.fiap.GlobalSolution.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface RoboRepository extends JpaRepository<Robo, Long> {
    Page<Robo> findByUltimaManutencaoAfter(LocalDate dataInicial, Pageable pageable);

    Page<Robo> findByUltimaManutencaoBefore(LocalDate dataInicial, Pageable pageable);

    Page<Robo> findByStatusIs(Status status, Pageable pageable);
}
