package med.voll.api.medico;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    public Medico(DadosCadastroMedico dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    private boolean ativo;

    @Embedded // fica numa class separada mas no bd faz parte da mesma tabela
    private Endereco endereco;

    public void atualizarInfo(DadosAtualizarMedico dados) {
        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if(dados.email() != null){
            this.email = dados.email();
        }
        if(dados.endereco() != null){
            this.endereco.atualizarEndereco(dados.endereco());
        }
        if(dados.especialidade() != null){
            this.especialidade = dados.especialidade();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}