package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.AuditLog;
import br.com.quickq.quickq_api.services.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@CrossOrigin(origins = "*")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping("/registrar")
    public AuditLog registrar(@RequestBody AuditLog log) {
        return auditLogService.registrarLog(log);
    }

    @GetMapping("/listar")
    public List<AuditLog> listar() {
        return auditLogService.listarTodos();
    }
}