package br.com.quickq.quickq_api.repositories;

import br.com.quickq.quickq_api.entities.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
}