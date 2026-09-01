package br.com.quickq.quickq_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "neuropsicologo")
public class Neuropsicologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "CRP é obrigatório")
    private String crp;

    private String whatsapp;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    private String endereco;

    @NotBlank(message = "Senha é obrigatória")
    @Column(name = "hash_senha")
    private String hashSenha;

    @OneToMany(mappedBy = "neuropsicologo")
    private List<AvaliacaoNeuro> avaliacoes;
}
