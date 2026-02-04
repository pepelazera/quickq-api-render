package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.Medicamento;
import br.com.quickq.quickq_api.services.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicamentos")
@CrossOrigin(origins = "*")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @PostMapping("/salvar")
    public Medicamento salvarNovoMedicamento(@RequestBody Medicamento medicamento) {
        return medicamentoService.salvarMedicamento(medicamento);
    }
}