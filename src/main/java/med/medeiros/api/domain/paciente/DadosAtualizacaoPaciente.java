package med.medeiros.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.medeiros.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        String email,
        DadosEndereco endereco
) {
}
