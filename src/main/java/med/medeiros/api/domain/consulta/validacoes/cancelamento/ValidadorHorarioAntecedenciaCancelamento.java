package med.medeiros.api.domain.consulta.validacoes.cancelamento;

import med.medeiros.api.domain.consulta.ConsultaRepository;
import med.medeiros.api.domain.consulta.validacoes.DadosCancelamentoConsulta;
import med.medeiros.api.domain.ecessao.ValidacaoExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class ValidadorHorarioAntecedenciaCancelamento implements ValidadorCancelamentoDeConsulta{
    @Autowired
    private ConsultaRepository consultaRepository;
    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora,consulta.getData()).toHours();

        if (diferencaEmHoras<24){
            throw new ValidacaoExcecao("Consulta só pode ser cancelada com antecedência mínima de 24h!");
        }

    }
}
