package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.Operador;
import br.com.quickq.quickq_api.services.OperadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // <-- Diz ao Spring que esta é a classe "Balcão" (API)
@RequestMapping("/api/operadores") // <-- Endereço base para esta API
@CrossOrigin(origins = "*") // <-- Permite que o seu frontend (em outro endereço) possa fazer pedidos
public class OperadorController {

    // "Injetando" o Cozinheiro (o Service)
    @Autowired
    private OperadorService operadorService;

    /**
     * Endpoint para salvar um novo operador.
     * Ele fica escutando por pedidos do tipo POST no endereço /api/operadores/salvar
     */
    @PostMapping("/salvar")
    public Operador salvarNovoOperador(@RequestBody Operador operador) {
        // Passa o pedido para o "Cozinheiro" (Service)
        return operadorService.salvarOperador(operador);
    }

    // Futuramente, podemos adicionar outros endpoints...
}