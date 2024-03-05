package med.medeiros.api.domain.consulta.validacoes;

import jakarta.validation.constraints.NotNull;
import med.medeiros.api.domain.consulta.MotivoCancelamento;

public record DadosCancelamentoConsulta(
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo

) {
}
