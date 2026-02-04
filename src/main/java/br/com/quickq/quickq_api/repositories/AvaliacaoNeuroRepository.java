package br.com.quickq.quickq_api.repositories;

import br.com.quickq.quickq_api.entities.AvaliacaoNeuro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoNeuroRepository extends JpaRepository<AvaliacaoNeuro, Long> {
    // Automaticamente ganha métodos save(), findById(), findAll(), delete(), etc.
    // para a entidade AvaliacaoNeuro.
}