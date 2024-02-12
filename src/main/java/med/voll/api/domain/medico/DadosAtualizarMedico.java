package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarMedico(
        @NotNull
        Long id,
        String telefone,
        String nome,
        String email,
        DadosEndereco endereco,
        Especialidade especialidade) {

}
