package br.com.quickq.quickq_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
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
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Idade é obrigatória")
    @Positive(message = "Idade deve ser maior que zero")
    private Integer idade;

    private String genero;
    private String atividadePrincipal;

    @NotBlank(message = "Responsável é obrigatório")
    private String responsavel;

    @NotBlank(message = "Diagnóstico é obrigatório")
    private String diagnostico;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    private String whatsapp;
    private String email;
    private String endereco;
    private LocalDate dataCadastro;

    @OneToMany(mappedBy = "cliente")
    private List<AvaliacaoNeuro> avaliacoes;
}
