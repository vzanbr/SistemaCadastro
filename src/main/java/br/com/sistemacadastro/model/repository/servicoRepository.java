package br.com.sistemacadastro.model.repository;

import br.com.sistemacadastro.model.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface servicoRepository extends JpaRepository<Servico, Integer> {
}
