package med.medeiros.api.domain.consulta.validacoes.agendamento;

import med.medeiros.api.domain.consulta.DadosAgendamento;
import med.medeiros.api.domain.ecessao.ValidacaoExcecao;
import med.medeiros.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorConsultaAgendamento {
    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DadosAgendamento dados) {
        if (dados.idPaciente() == null) {
            return;
        }
        var pacienteAtivo = pacienteRepository.findAtivoById(dados.idPaciente());
        if (!pacienteAtivo) {
            throw new ValidacaoExcecao("Paciente não está ativo! ");
        }

    }
}
