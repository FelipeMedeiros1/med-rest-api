package med.medeiros.api.controller;


import med.medeiros.api.domain.consulta.ConsultaAgendamentoService;
import med.medeiros.api.domain.consulta.DadosAgendamento;
import med.medeiros.api.domain.consulta.DadosDetalheConsulta;
import med.medeiros.api.domain.endereco.DadosEndereco;
import med.medeiros.api.domain.medico.DadosCadastroMedico;
import med.medeiros.api.domain.medico.Especialidade;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DadosAgendamento> dadosAgendamentoJson;
    @Autowired
    private JacksonTester<DadosDetalheConsulta> dadosDetalheConsultaJson;
    @MockBean
    private ConsultaAgendamentoService consulta;


    @Test
    @DisplayName("Devolver statusCode 400 quando informações forem inválidas")
    @WithMockUser
    public void consulta_cenario01() throws Exception {

        var resp = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        Assertions.assertThat(resp.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Devolver statusCode 200 quando informações forem corretas")
    @WithMockUser
    public void consulta_cenario02() throws Exception {

        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamento = new DadosDetalheConsulta(null, 9l, 1l, data);

        when(consulta.agendar(any())).thenReturn(dadosDetalhamento);

        var resp = mvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAgendamentoJson.write(
                                        new DadosAgendamento(9l, 1l, data, especialidade)).getJson())
                )
                .andReturn().getResponse();

        Assertions.assertThat(resp.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalheConsultaJson.write(dadosDetalhamento).getJson();

        Assertions.assertThat(resp.getContentAsString()).isEqualTo(jsonEsperado);

    }
    @Test
    @DisplayName("Devolver statusCode 201 quando cadastrar com sucesso")
    @WithMockUser
    public void consulta_cenario03() throws Exception {

        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamento = new DadosDetalheConsulta(null, 9l, 1l, data);
        var endereco = new DadosEndereco("rua","12345620","A","São Paulo","SP","1","02");
        var medico= new DadosCadastroMedico("Medico","m@medico.com","123456789","123456",especialidade,endereco);

        when(consulta.agendar(any())).thenReturn(dadosDetalhamento);

        var resp = mvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAgendamentoJson.write(
                                        new DadosAgendamento(9l, 1l, data, especialidade)).getJson())
                )
                .andReturn().getResponse();

        Assertions.assertThat(resp.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalheConsultaJson.write(dadosDetalhamento).getJson();

        Assertions.assertThat(resp.getContentAsString()).isEqualTo(jsonEsperado);

    }

}