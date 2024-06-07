package br.com.fiap.GlobalSolution.repository;

import br.com.fiap.GlobalSolution.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
