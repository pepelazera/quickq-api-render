package br.com.quickq.quickq_api.services;

import br.com.quickq.quickq_api.entities.Neurofeedback;
import br.com.quickq.quickq_api.repositories.NeurofeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NeurofeedbackService {

    @Autowired
    private NeurofeedbackRepository neurofeedbackRepository;

    public Neurofeedback salvarSessao(Neurofeedback neurofeedback) {
        return neurofeedbackRepository.save(neurofeedback);
    }
}