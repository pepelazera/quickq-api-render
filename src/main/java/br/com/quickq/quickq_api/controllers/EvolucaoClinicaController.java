package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.EvolucaoClinica;
import br.com.quickq.quickq_api.services.EvolucaoClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/evolucao")
@CrossOrigin(origins = "*")
public class EvolucaoClinicaController {

    @Autowired
    private EvolucaoClinicaService evolucaoService;

    @PostMapping("/salvar")
    public EvolucaoClinica salvar(@RequestBody EvolucaoClinica evolucao) {
        return evolucaoService.salvar(evolucao);
    }

    @GetMapping("/cliente/{id}")
    public List<EvolucaoClinica> listarPorCliente(@PathVariable Long id) {
        return evolucaoService.listarPorCliente(id);
    }
}