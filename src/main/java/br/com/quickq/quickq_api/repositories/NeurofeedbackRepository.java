package br.com.quickq.quickq_api.repositories;

import br.com.quickq.quickq_api.entities.Neurofeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeurofeedbackRepository extends JpaRepository<Neurofeedback, Long> {
}