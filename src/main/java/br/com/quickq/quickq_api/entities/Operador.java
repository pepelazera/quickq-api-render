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
@Table(name = "operador")
public class Operador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String formacao;

    @Column(name = "registro_conselho")
    private String registroConselho;

    private String whatsapp;
    private String email;
    private String endereco;

    @Column(name = "hash_senha")
    private String hashSenha;

    @OneToMany(mappedBy = "operador")
    private List<AvaliacaoNeuro> avaliacoes;
}