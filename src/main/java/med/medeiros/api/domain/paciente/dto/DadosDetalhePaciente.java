package med.medeiros.api.domain.paciente.dto;

import med.medeiros.api.domain.endereco.Endereco;
import med.medeiros.api.domain.paciente.Paciente;

public record DadosDetalhePaciente(
        Long id,
        String nome,
        String email,
        String telefone,
        Endereco endereco


) {
    public DadosDetalhePaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getEndereco());
    }
}
