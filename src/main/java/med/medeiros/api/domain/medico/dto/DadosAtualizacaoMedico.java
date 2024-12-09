package med.medeiros.api.domain.medico.dto;

import jakarta.validation.constraints.NotNull;
import med.medeiros.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        String email,
        DadosEndereco endereco
) {
}
