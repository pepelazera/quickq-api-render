package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.Cliente;
import br.com.quickq.quickq_api.services.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClienteControllerValidationTests {

    @Test
    void naoDeveSalvarClienteComCamposObrigatoriosVazios() throws Exception {
        ClienteServiceFake clienteService = new ClienteServiceFake();
        MockMvc mockMvc = criarMockMvc(clienteService);

        mockMvc.perform(post("/api/clientes/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": " ",
                                  "idade": null,
                                  "responsavel": "",
                                  "diagnostico": "  "
                                }
                                """))
                .andExpect(status().isBadRequest());

        assertFalse(clienteService.salvarFoiChamado);
    }

    @Test
    void deveSalvarClienteQuandoCamposObrigatoriosEstaoPreenchidos() throws Exception {
        ClienteServiceFake clienteService = new ClienteServiceFake();
        MockMvc mockMvc = criarMockMvc(clienteService);

        mockMvc.perform(post("/api/clientes/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Cliente Teste",
                                  "idade": 30,
                                  "responsavel": "Responsável Teste",
                                  "diagnostico": "Diagnóstico Teste"
                                }
                                """))
                .andExpect(status().isOk());

        assertTrue(clienteService.salvarFoiChamado);
    }

    private MockMvc criarMockMvc(ClienteService clienteService) {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        return MockMvcBuilders
                .standaloneSetup(new ClienteController(clienteService))
                .setValidator(validator)
                .build();
    }

    private static class ClienteServiceFake extends ClienteService {
        private boolean salvarFoiChamado;

        @Override
        public Cliente salvarCliente(Cliente cliente) {
            salvarFoiChamado = true;
            return null;
        }
    }
}
