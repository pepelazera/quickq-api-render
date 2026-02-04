package br.com.quickq.quickq_api.entities;

import jakarta.persistence.*;
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

    private String nome;
    private int idade;
    private String genero;
    private String atividadePrincipal;
    private String responsavel;
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