package br.com.quickq.quickq_api.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // O slide mostra que o medicamento é ligado a um cliente
    // Mas no cadastro, muitas vezes salvamos apenas o nome do cliente ou ID de referência
    // Para simplificar o cadastro direto, vamos usar um campo de texto ou ID por enquanto.
    @Column(name = "id_cliente_ref")
    private String idClienteRef;

    private String nome;

    @Column(name = "principio_ativo")
    private String principioAtivo;

    private String posologia;

    @Column(name = "dosagem_atual")
    private String dosagemAtual;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    private String medico; // Nome do médico que prescreveu

    private String reacoes;
}