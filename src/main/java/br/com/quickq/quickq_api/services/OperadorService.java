package br.com.quickq.quickq_api.services;

import br.com.quickq.quickq_api.entities.Operador;
import br.com.quickq.quickq_api.repositories.OperadorRepository; // Verifique se este é o nome do seu repositório
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // <-- Diz ao Spring que esta é a classe "Cozinheiro"
public class OperadorService {

    // "Injetando" o kit de ferramentas do banco de dados
    @Autowired
    private OperadorRepository operadorRepository;

    /**
     * Lógica de negócio para salvar um operador.
     * @param operador O objeto Operador vindo do "balcão" (Controller)
     * @return O Operador salvo (com o ID preenchido)
     */
    public Operador salvarOperador(Operador operador) {
        // (Aqui no futuro você pode adicionar lógicas, como criptografar a senha)
        // Por enquanto, apenas salva:
        return operadorRepository.save(operador);
    }

    // Futuramente, podemos adicionar outros métodos...
}