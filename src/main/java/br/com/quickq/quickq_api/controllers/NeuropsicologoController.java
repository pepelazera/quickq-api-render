package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.Neuropsicologo;
import br.com.quickq.quickq_api.services.NeuropsicologoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController // <-- Diz ao Spring que esta é a classe "Balcão" (API)
@RequestMapping("/api/neuropsicologos") // <-- Endereço base para esta API
@CrossOrigin(origins = "*") // <-- Permite que o seu frontend (em outro endereço) possa fazer pedidos
public class NeuropsicologoController {

    // "Injetando" o Cozinheiro (o Service)
    private final NeuropsicologoService neuropsicologoService;

    public NeuropsicologoController(NeuropsicologoService neuropsicologoService) {
        this.neuropsicologoService = neuropsicologoService;
    }

    /**
     * Endpoint para salvar um novo neuropsicólogo.
     * Ele fica escutando por pedidos do tipo POST no endereço /api/neuropsicologos/salvar
     */
    @PostMapping("/salvar")
    public Neuropsicologo salvarNovoNeuropsicologo(@Valid @RequestBody Neuropsicologo neuropsicologo) {
        // Passa o pedido para o "Cozinheiro" (Service)
        return neuropsicologoService.salvarNeuropsicologo(neuropsicologo);
    }

    // Futuramente, podemos adicionar outros endpoints:
    // @GetMapping("/buscar")
    // public List<Neuropsicologo> buscarTodos() { ... }
}
