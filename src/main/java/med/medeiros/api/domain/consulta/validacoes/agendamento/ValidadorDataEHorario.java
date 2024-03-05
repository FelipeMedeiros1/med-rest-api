package med.medeiros.api.domain.consulta.validacoes.agendamento;

import med.medeiros.api.domain.consulta.ConsultaRepository;
import med.medeiros.api.domain.consulta.DadosAgendamento;
import med.medeiros.api.domain.ecessao.ValidacaoExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorDataEHorario implements ValidadorConsultaAgendamento {
    @Autowired
    private ConsultaRepository consultaRepository;
    public void validar(DadosAgendamento dados) {
        LocalDateTime dataHoraConsulta = dados.data();
        LocalDateTime dataHoraFimConsulta = dataHoraConsulta.plusMinutes(60);

        boolean existeConsultaNoHorario = consultaRepository.existsByMedicoIdAndDataBetween(dados.idMedico(), dataHoraConsulta, dataHoraFimConsulta);

        if (existeConsultaNoHorario) {
            throw new ValidacaoExcecao("Médico possui consulta nesse horário!!!");
        }
    }


}
