package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.AvaliacaoNeuro;
import br.com.quickq.quickq_api.services.AvaliacaoNeuroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
@CrossOrigin(origins = "*")
public class AvaliacaoNeuroController {

    @Autowired
    private AvaliacaoNeuroService avaliacaoNeuroService;

    @PostMapping("/salvar")
    public AvaliacaoNeuro salvarAvaliacao(@RequestBody AvaliacaoNeuro avaliacao) {
        return avaliacaoNeuroService.salvarAvaliacao(avaliacao);
    }

    @GetMapping("/listar")
    public List<AvaliacaoNeuro> listarAvaliacoes() {
        return avaliacaoNeuroService.buscarTodas();
    }
}