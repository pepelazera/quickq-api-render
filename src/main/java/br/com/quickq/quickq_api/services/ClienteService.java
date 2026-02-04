package br.com.quickq.quickq_api.services;

import br.com.quickq.quickq_api.entities.Cliente;
import br.com.quickq.quickq_api.repositories.RepositorioDeCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // <-- Diz ao Spring que esta é a classe "Cozinheiro"
public class ClienteService {

    // "Injetando" o kit de ferramentas do banco de dados
    @Autowired
    private RepositorioDeCliente clienteRepository;

    /**
     * Lógica de negócio para salvar um cliente.
     * (Por enquanto, é simples, mas no futuro pode ter validações, etc.)
     * @param cliente O objeto Cliente vindo do "balcão" (Controller)
     * @return O Cliente salvo (com o ID preenchido)
     */
    public Cliente salvarCliente(Cliente cliente) {
        // Usa a ferramenta "save" do repositório
        return clienteRepository.save(cliente);
    }

    // Futuramente, podemos adicionar outros métodos, como:
    // public List<Cliente> buscarTodos() { ... }
    // public Cliente buscarPorId(Long id) { ... }
}