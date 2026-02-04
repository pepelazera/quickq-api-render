package br.com.quickq.quickq_api.repositories;

import br.com.quickq.quickq_api.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUsuarioId(Long usuarioId);
}