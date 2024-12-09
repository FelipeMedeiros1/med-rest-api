package med.medeiros.api.domain.consulta.validacoes.agendamento;

import med.medeiros.api.domain.consulta.dto.DadosAgendamento;
import med.medeiros.api.domain.ecessao.ValidacaoExcecao;
import med.medeiros.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorConsultaAgendamento {
    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamento dados) {
        if (dados.idMedico() == null) {
            return;
        }
        var medicoAtivo = medicoRepository.findAtivoById(dados.idMedico());
        if (!medicoAtivo) {

            throw new ValidacaoExcecao("O médico não está ativo! ");
        }
    }
}
