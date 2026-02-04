package br.com.quickq.quickq_api.repositories;

import br.com.quickq.quickq_api.entities.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    // Automaticamente ganha métodos save(), findById(), findAll(), delete(), etc.
    // para a entidade Equipamento.
}