package br.com.quickq.quickq_api.services;

import br.com.quickq.quickq_api.entities.AuditLog;
import br.com.quickq.quickq_api.repositories.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public AuditLog registrarLog(AuditLog log) {
        if (log.getCreatedAt() == null) {
            log.setCreatedAt(LocalDateTime.now());
        }
        return auditLogRepository.save(log);
    }

    public List<AuditLog> listarTodos() {
        return auditLogRepository.findAll();
    }
}