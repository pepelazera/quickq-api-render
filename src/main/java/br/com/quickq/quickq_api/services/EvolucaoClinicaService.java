package br.com.quickq.quickq_api.services;

import br.com.quickq.quickq_api.entities.EvolucaoClinica;
import br.com.quickq.quickq_api.repositories.EvolucaoClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvolucaoClinicaService {

    @Autowired
    private EvolucaoClinicaRepository evolucaoRepository;

    public EvolucaoClinica salvar(EvolucaoClinica evolucao) {
        if (evolucao.getId() == null) {
            evolucao.setCreatedAt(LocalDateTime.now());
        }
        evolucao.setUpdatedAt(LocalDateTime.now());
        return evolucaoRepository.save(evolucao);
    }

    public List<EvolucaoClinica> listarPorCliente(Long clienteId) {
        return evolucaoRepository.findByClienteId(clienteId);
    }
}