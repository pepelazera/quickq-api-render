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
@Table(name = "medico")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especializacao;
    private String crm;
    private String rqe;
    private String whatsapp;
    private String email;
    private String endereco;

    @OneToMany(mappedBy = "medico")
    private List<Cliente> clientes;

    @OneToMany(mappedBy = "medico")
    private List<AvaliacaoNeuro> avaliacoes;
}