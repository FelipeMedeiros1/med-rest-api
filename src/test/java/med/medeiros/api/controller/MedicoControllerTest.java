package med.medeiros.api.controller;


import med.medeiros.api.domain.endereco.DadosEndereco;
import med.medeiros.api.domain.medico.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroJson;
    @Autowired
    private JacksonTester<DadosDetalheMedico> detalheMedicoJson;
    @MockBean
    private MedicoRepository repository;


    @Test
    @DisplayName("Devolver statusCode 201 quando cadastrar com sucesso")
    @WithMockUser
    public void cadastroMedico_cenario01() throws Exception {
        var especialidade = Especialidade.CARDIOLOGIA;
        var endereco = new DadosEndereco("rua", "12345620", "A", "Sao Paulo", "SP", "1", "02");
        var medico = new DadosCadastroMedico("Medico", "m@medico.com", "123456789", "123456", especialidade, endereco);

        var medicoCadastrado = new Medico(medico);

        when(repository.save(any())).thenReturn(medicoCadastrado);

        var resp = mvc
                .perform(
                        post("/medicos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosCadastroJson.write(medico).getJson())
                )
                .andReturn().getResponse();

        Assertions.assertThat(resp.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(resp.getContentAsString()).isEqualTo(detalheMedicoJson.write(new DadosDetalheMedico(medicoCadastrado)).getJson());
    }
}

