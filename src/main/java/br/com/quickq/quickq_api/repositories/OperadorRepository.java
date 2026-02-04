package br.com.quickq.quickq_api.repositories;

import br.com.quickq.quickq_api.entities.Operador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperadorRepository extends JpaRepository<Operador, Long> {
    // Automaticamente ganha métodos save(), findById(), findAll(), delete(), etc.
    // para a entidade Operador.
}