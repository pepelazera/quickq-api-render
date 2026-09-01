package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.Neuropsicologo;
import br.com.quickq.quickq_api.services.NeuropsicologoService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NeuropsicologoControllerValidationTests {

    @Test
    void naoDeveSalvarNeuropsicologoComCamposObrigatoriosVazios() throws Exception {
        NeuropsicologoServiceFake service = new NeuropsicologoServiceFake();
        MockMvc mockMvc = criarMockMvc(service);

        mockMvc.perform(post("/api/neuropsicologos/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": " ",
                                  "crp": "",
                                  "email": "  ",
                                  "hashSenha": null
                                }
                                """))
                .andExpect(status().isBadRequest());

        assertFalse(service.salvarFoiChamado);
    }

    @Test
    void deveSalvarNeuropsicologoQuandoCamposObrigatoriosEstaoPreenchidos() throws Exception {
        NeuropsicologoServiceFake service = new NeuropsicologoServiceFake();
        MockMvc mockMvc = criarMockMvc(service);

        mockMvc.perform(post("/api/neuropsicologos/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Profissional Teste",
                                  "crp": "00/123456",
                                  "email": "profissional@example.com",
                                  "hashSenha": "senha-segura"
                                }
                                """))
                .andExpect(status().isOk());

        assertTrue(service.salvarFoiChamado);
    }

    private MockMvc criarMockMvc(NeuropsicologoService service) {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        return MockMvcBuilders
                .standaloneSetup(new NeuropsicologoController(service))
                .setValidator(validator)
                .build();
    }

    private static class NeuropsicologoServiceFake extends NeuropsicologoService {
        private boolean salvarFoiChamado;

        @Override
        public Neuropsicologo salvarNeuropsicologo(Neuropsicologo neuropsicologo) {
            salvarFoiChamado = true;
            return null;
        }
    }
}
