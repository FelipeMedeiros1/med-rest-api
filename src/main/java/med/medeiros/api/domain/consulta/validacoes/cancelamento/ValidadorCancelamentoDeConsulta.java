package med.medeiros.api.domain.consulta.validacoes.cancelamento;

import med.medeiros.api.domain.consulta.validacoes.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {
    void validar(DadosCancelamentoConsulta dados);
}
