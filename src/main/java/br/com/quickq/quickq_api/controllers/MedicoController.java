package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.Medico;
import br.com.quickq.quickq_api.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicos")
@CrossOrigin(origins = "*")

public class MedicoController {

    // "Injetando" o Cozinheiro (o Service)
    @Autowired
    private MedicoService medicoService;


    @PostMapping("/salvar")
    public Medico salvarNovoMedico(@RequestBody Medico medico) {
        // Passa o pedido para o "Cozinheiro" (Service)
        return medicoService.salvarMedico(medico);
    }

    // Futuramente, podemos adicionar outros endpoints...
}