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
@Table(name = "equipamento")
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String marca;
    private String modelo;

    @Column(name = "canais_eeg")
    private int canaisEeg;

    @Column(name = "outros_canais")
    private int outrosCanais;

    private String fabricante;

    @Column(name = "numero_serie")
    private String numeroSerie;

    @Column(name = "nota_fiscal")
    private String notaFiscal;

    private String patrimonio;
    private boolean ativo;

    @Column(name = "avaliacao_neuro")
    private String avaliacaoNeuro;

    @OneToMany(mappedBy = "equipamento")
    private List<AvaliacaoNeuro> avaliacoes;
}