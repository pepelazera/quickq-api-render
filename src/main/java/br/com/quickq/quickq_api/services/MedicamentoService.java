package br.com.quickq.quickq_api.services;

import br.com.quickq.quickq_api.entities.Medicamento;
import br.com.quickq.quickq_api.repositories.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    public Medicamento salvarMedicamento(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }
}