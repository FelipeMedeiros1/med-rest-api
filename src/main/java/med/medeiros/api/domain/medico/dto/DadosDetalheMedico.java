package med.medeiros.api.domain.medico.dto;

import med.medeiros.api.domain.endereco.Endereco;
import med.medeiros.api.domain.medico.Especialidade;
import med.medeiros.api.domain.medico.Medico;

public record DadosDetalheMedico(
        Long id,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        Endereco endereco
        ) {
    public DadosDetalheMedico(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade(),medico.getEndereco());
    }

}
