package br.com.quickq.quickq_api.services;

import br.com.quickq.quickq_api.entities.Medico;
import br.com.quickq.quickq_api.repositories.MedicoRepository; // Verifique se este é o nome do seu repositório
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {


    @Autowired
    private MedicoRepository medicoRepository;


    public Medico salvarMedico(Medico medico) {
        // Por enquanto, apenas salva:
        return medicoRepository.save(medico);
    }

    // Futuramente, podemos adicionar outros métodos...
}