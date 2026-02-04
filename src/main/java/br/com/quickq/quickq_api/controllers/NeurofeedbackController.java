package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.Neurofeedback;
import br.com.quickq.quickq_api.services.NeurofeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/neurofeedback") // O endereço correto!
@CrossOrigin(origins = "*")
public class NeurofeedbackController {

    @Autowired
    private NeurofeedbackService neurofeedbackService;

    @PostMapping("/salvar")
    public Neurofeedback salvarNovaSessao(@RequestBody Neurofeedback neurofeedback) {
        return neurofeedbackService.salvarSessao(neurofeedback);
    }
}