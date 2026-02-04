package br.com.quickq.quickq_api.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "neurofeedback")
public class Neurofeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cliente_ref")
    private String idClienteRef;

    @Column(name = "id_equipamento_ref")
    private String idEquipamentoRef;

    private LocalDate data;

    @Column(name = "sessao_numero")
    private String sessaoNumero;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_termino")
    private LocalTime horaTermino;

    private String duracao;

    @Column(name = "estado_paciente")
    private String estadoPaciente;

    private String descrever;

    @Column(name = "condicoes_fisiologicas")
    private String condicoesFisiologicas;

    private String protocolos;

    @Column(name = "frequencias_alvos")
    private String frequenciasAlvos;

    private String artefatos;

    @Column(name = "ajustes_thresholds")
    private String ajustesThresholds;

    @Column(name = "obs_clinicas")
    private String obsClinicas;

    @Column(name = "feedback_paciente")
    private String feedbackPaciente;
}