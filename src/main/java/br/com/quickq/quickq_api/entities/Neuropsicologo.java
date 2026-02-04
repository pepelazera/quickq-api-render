package br.com.quickq.quickq_api.entities;

import jakarta.persistence.*;
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

    private String nome;
    private String crp;
    private String whatsapp;
    private String email;
    private String endereco;

    @Column(name = "hash_senha")
    private String hashSenha;

    @OneToMany(mappedBy = "neuropsicologo")
    private List<AvaliacaoNeuro> avaliacoes;
}