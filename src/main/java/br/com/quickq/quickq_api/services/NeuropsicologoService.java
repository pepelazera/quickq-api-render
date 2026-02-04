package br.com.quickq.quickq_api.services;

import br.com.quickq.quickq_api.entities.Neuropsicologo;
import br.com.quickq.quickq_api.repositories.NeuropsicologoRepository; // Verifique se este é o nome do seu repositório
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // <-- Diz ao Spring que esta é a classe "Cozinheiro"
public class NeuropsicologoService {

    // "Injetando" o kit de ferramentas do banco de dados
    @Autowired
    private NeuropsicologoRepository neuropsicologoRepository;

    /**
     * Lógica de negócio para salvar um neuropsicólogo.
     * @param neuropsicologo O objeto Neuropsicologo vindo do "balcão" (Controller)
     * @return O Neuropsicologo salvo (com o ID preenchido)
     */
    public Neuropsicologo salvarNeuropsicologo(Neuropsicologo neuropsicologo) {
        // (Aqui no futuro você pode adicionar lógicas, como criptografar a senha)
        // Por enquanto, apenas salva:
        return neuropsicologoRepository.save(neuropsicologo);
    }

    // Futuramente, podemos adicionar outros métodos, como:
    // public List<Neuropsicologo> buscarTodos() { ... }
    // public Neuropsicologo buscarPorCrp(String crp) { ... }
}