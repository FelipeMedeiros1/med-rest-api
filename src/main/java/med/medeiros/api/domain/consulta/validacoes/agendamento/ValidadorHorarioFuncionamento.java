package med.medeiros.api.domain.consulta.validacoes.agendamento;

import med.medeiros.api.domain.consulta.DadosAgendamento;
import med.medeiros.api.domain.ecessao.ValidacaoExcecao;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorConsultaAgendamento {
    public void validar(DadosAgendamento dados) {
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDoHorario = dataConsulta.getHour() < 7;
        var depoisDoHorario = dataConsulta.getHour() > 18;

        if (domingo || antesDoHorario || depoisDoHorario) {
            throw new ValidacaoExcecao("Fora do Horário de agendamento");
        }
    }
}
