package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.DocsIa;
import br.com.quickq.quickq_api.services.DocsIaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "*")
public class DocsIaController {

    @Autowired
    private DocsIaService docsIaService;

    @PostMapping("/salvar")
    public DocsIa salvarDocumento(@RequestBody DocsIa documento) {
        return docsIaService.salvarDocumento(documento);
    }

    @GetMapping("/listar")
    public List<DocsIa> listarDocumentos() {
        return docsIaService.buscarTodos();
    }
}