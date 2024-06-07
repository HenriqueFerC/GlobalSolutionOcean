package br.com.fiap.GlobalSolution.repository;

import br.com.fiap.GlobalSolution.model.Coleta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ColetaRepository extends JpaRepository<Coleta, Long> {
    Page<Coleta> findByQuantidadeColetadaLessThan(Long quantidadeColetada, Pageable pageable);

    Page<Coleta> findByQuantidadeColetadaGreaterThan(Long quantidadeColetada, Pageable pageable);

    Page<Coleta> findByQuantidadeColetadaBetween(Long quantidadeColetadaInicial, Long quantidadeColetadaFinal, Pageable pageable);

    Page<Coleta> findByDataColetadaAfter(LocalDate dataInicial, Pageable pageable);

    Page<Coleta> findByDataColetadaBefore(LocalDate dataInicial, Pageable pageable);

    Page<Coleta> findByDataColetadaBetween(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable);
}
