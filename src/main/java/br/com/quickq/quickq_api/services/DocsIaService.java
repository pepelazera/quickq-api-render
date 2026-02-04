package br.com.quickq.quickq_api.services;

import br.com.quickq.quickq_api.entities.DocsIa;
import br.com.quickq.quickq_api.repositories.DocsIaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocsIaService {

    @Autowired
    private DocsIaRepository docsIaRepository;

    public DocsIa salvarDocumento(DocsIa documento) {
        if (documento.getCreatedAt() == null) {
            documento.setCreatedAt(LocalDateTime.now());
        }
        return docsIaRepository.save(documento);
    }

    public List<DocsIa> buscarTodos() {
        return docsIaRepository.findAll();
    }
}