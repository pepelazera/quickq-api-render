package br.com.quickq.quickq_api.services;

import br.com.quickq.quickq_api.entities.Equipamento;
import br.com.quickq.quickq_api.repositories.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    public Equipamento salvarEquipamento(Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }
}