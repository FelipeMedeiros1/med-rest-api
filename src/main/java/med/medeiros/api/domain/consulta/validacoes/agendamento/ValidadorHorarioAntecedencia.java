package med.medeiros.api.domain.consulta.validacoes.agendamento;

import med.medeiros.api.domain.consulta.dto.DadosAgendamento;
import med.medeiros.api.domain.ecessao.ValidacaoExcecao;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorConsultaAgendamento {
    public void validar(DadosAgendamento dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidacaoExcecao("O agendamento deve ser feito com 30 minutos de antecedência!");
        }
    }
}
