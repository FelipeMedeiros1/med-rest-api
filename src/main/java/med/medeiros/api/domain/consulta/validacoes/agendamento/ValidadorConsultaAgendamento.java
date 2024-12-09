package med.medeiros.api.domain.consulta.validacoes.agendamento;

import med.medeiros.api.domain.consulta.dto.DadosAgendamento;

public interface ValidadorConsultaAgendamento {
    void validar(DadosAgendamento dados);

}
