package br.com.quickq.quickq_api.services;

import br.com.quickq.quickq_api.entities.AvaliacaoNeuro;
import br.com.quickq.quickq_api.repositories.AvaliacaoNeuroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvaliacaoNeuroService {

    @Autowired
    private AvaliacaoNeuroRepository avaliacaoNeuroRepository;

    public AvaliacaoNeuro salvarAvaliacao(AvaliacaoNeuro avaliacao) {
        // Se não tiver data de processamento, define como hoje
        if (avaliacao.getDataProcessamento() == null) {
            avaliacao.setDataProcessamento(LocalDate.now());
        }
        return avaliacaoNeuroRepository.save(avaliacao);
    }

    public List<AvaliacaoNeuro> buscarTodas() {
        return avaliacaoNeuroRepository.findAll();
    }

    public AvaliacaoNeuro buscarPorId(Long id) {
        return avaliacaoNeuroRepository.findById(id).orElse(null);
    }
}