package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.Equipamento;
import br.com.quickq.quickq_api.services.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipamentos")
@CrossOrigin(origins = "*")
public class EquipamentoController {

    @Autowired
    private EquipamentoService equipamentoService;

    @PostMapping("/salvar")
    public Equipamento salvarNovoEquipamento(@RequestBody Equipamento equipamento) {
        return equipamentoService.salvarEquipamento(equipamento);
    }
}