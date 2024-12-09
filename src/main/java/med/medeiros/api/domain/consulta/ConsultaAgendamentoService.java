package med.medeiros.api.domain.consulta;

import med.medeiros.api.domain.consulta.dto.DadosAgendamento;
import med.medeiros.api.domain.consulta.dto.DadosDetalheConsulta;
import med.medeiros.api.domain.consulta.validacoes.DadosCancelamentoConsulta;
import med.medeiros.api.domain.consulta.validacoes.agendamento.ValidadorConsultaAgendamento;
import med.medeiros.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.medeiros.api.domain.ecessao.ValidacaoExcecao;
import med.medeiros.api.domain.medico.Medico;
import med.medeiros.api.domain.medico.MedicoRepository;
import med.medeiros.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaAgendamentoService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private List<ValidadorConsultaAgendamento> validadores;
    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalheConsulta agendar(DadosAgendamento dados) {
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoExcecao("Id do Médico não existe!");
        }
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoExcecao("Id do Paciente não existe!");
        }

        validadores.forEach(v -> v.validar(dados));

        var medico = escollherMedico(dados);
        if (medico == null){
            throw new ValidacaoExcecao("Não existe Médico disponível nessa data");
        }
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var consulta = new Consulta(null, medico, paciente, dados.data(),null);
        consultaRepository.save(consulta);

        return new DadosDetalheConsulta(consulta);
    }

    private Medico escollherMedico(DadosAgendamento dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoExcecao("Especialidade Obrigatória!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados){
        if (!consultaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoExcecao("Id da consulta não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());

    }


}
