package br.com.quickq.quickq_api.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "avaliacao_neuro")
public class AvaliacaoNeuro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_operador")
    private Operador operador;

    @ManyToOne
    @JoinColumn(name = "id_neuropsicologo")
    private Neuropsicologo neuropsicologo;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "id_equipamento")
    private Equipamento equipamento;

    // Campos de Dados (Estes eram os que estavam sem Getters/Setters)
    @Column(name = "data_processamento")
    private LocalDate dataProcessamento;

    @Column(name = "data_do_egg")
    private LocalDate dataDoEgg;

    @Column(name = "data_avaliacao_inicial")
    private LocalDate dataAvaliacaoInicial;

    private String avaliacao; // num. sequencial ou tipo

    @Column(name = "neurofeedback_sessoes")
    private int neurofeedbackSessoes;

    private String obs;

    @OneToMany(mappedBy = "avaliacaoNeuro")
    private List<DocsIa> documentos;

    // --- CONSTRUTORES ---
    public AvaliacaoNeuro() {}

    // --- GETTERS E SETTERS MANUAIS (COMPLETOS) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Neuropsicologo getNeuropsicologo() {
        return neuropsicologo;
    }

    public void setNeuropsicologo(Neuropsicologo neuropsicologo) {
        this.neuropsicologo = neuropsicologo;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    // --- AQUI ESTAVAM FALTANDO ---

    public LocalDate getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(LocalDate dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public LocalDate getDataDoEgg() {
        return dataDoEgg;
    }

    public void setDataDoEgg(LocalDate dataDoEgg) {
        this.dataDoEgg = dataDoEgg;
    }

    public LocalDate getDataAvaliacaoInicial() {
        return dataAvaliacaoInicial;
    }

    public void setDataAvaliacaoInicial(LocalDate dataAvaliacaoInicial) {
        this.dataAvaliacaoInicial = dataAvaliacaoInicial;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public int getNeurofeedbackSessoes() {
        return neurofeedbackSessoes;
    }

    public void setNeurofeedbackSessoes(int neurofeedbackSessoes) {
        this.neurofeedbackSessoes = neurofeedbackSessoes;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public List<DocsIa> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocsIa> documentos) {
        this.documentos = documentos;
    }
}