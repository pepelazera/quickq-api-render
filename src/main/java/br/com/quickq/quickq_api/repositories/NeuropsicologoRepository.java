package br.com.quickq.quickq_api.repositories;

import br.com.quickq.quickq_api.entities.Neuropsicologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeuropsicologoRepository extends JpaRepository<Neuropsicologo, Long> {
    // Automaticamente ganha métodos save(), findById(), findAll(), delete(), etc.
    // para a entidade Neuropsicologo.
}