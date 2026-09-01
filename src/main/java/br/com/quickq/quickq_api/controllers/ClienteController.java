package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.Cliente;
import br.com.quickq.quickq_api.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController // <-- Diz ao Spring que esta é a classe "Balcão" (API)
@RequestMapping("/api/clientes") // <-- Define o endereço base para todos os métodos
@CrossOrigin(origins = "*") // <-- **MUITO IMPORTANTE**: Permite que o seu frontend (em outro endereço) possa fazer pedidos
public class ClienteController {

    // "Injetando" o Cozinheiro (o Service)
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Este é o nosso primeiro "endpoint" (prato do cardápio).
     * Ele fica escutando por pedidos do tipo POST no endereço /api/clientes/salvar
     *
     * @param cliente Os dados do cliente que o JavaScript (frontend) enviou no corpo do pedido
     * @return O Cliente que foi salvo no banco
     */
    @PostMapping("/salvar")
    public Cliente salvarNovoCliente(@Valid @RequestBody Cliente cliente) {
        // Passa o pedido para o "Cozinheiro" (Service)
        return clienteService.salvarCliente(cliente);
    }

    // Futuramente, podemos adicionar outros endpoints:
    // @GetMapping("/buscar/{id}")
    // public Cliente buscarClientePorId(@PathVariable Long id) { ... }
}
