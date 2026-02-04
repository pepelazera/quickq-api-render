package br.com.quickq.quickq_api.repositories;

import br.com.quickq.quickq_api.entities.EvolucaoClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EvolucaoClinicaRepository extends JpaRepository<EvolucaoClinica, Long> {
    // Busca evoluções de um cliente específico
    List<EvolucaoClinica> findByClienteId(Long clienteId);
}