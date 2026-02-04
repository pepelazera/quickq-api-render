package br.com.quickq.quickq_api.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "docs_ia")
public class DocsIa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_avaliacao")
    private AvaliacaoNeuro avaliacaoNeuro;

    @Column(name = "nome_doc")
    private String nomeDoc;

    @Column(name = "extensao_doc")
    private String extensaoDoc;

    @Column(name = "tipo_doc")
    private String tipoDoc;

    @Lob
    private String conteudo;

    @Column(name = "entrada_saida")
    private boolean entradaSaida;

    @Column(name = "caminho_arquivo")
    private String caminhoArquivo;

    @Column(name = "tamanho_bytes")
    private Long tamanhoBytes;

    @Column(name = "hash_sha256")
    private String hashSha256;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // --- Construtores ---
    public DocsIa() {}

    public DocsIa(Long id, AvaliacaoNeuro avaliacaoNeuro, String nomeDoc, String extensaoDoc, String tipoDoc, String conteudo, boolean entradaSaida, String caminhoArquivo, Long tamanhoBytes, String hashSha256, LocalDateTime createdAt) {
        this.id = id;
        this.avaliacaoNeuro = avaliacaoNeuro;
        this.nomeDoc = nomeDoc;
        this.extensaoDoc = extensaoDoc;
        this.tipoDoc = tipoDoc;
        this.conteudo = conteudo;
        this.entradaSaida = entradaSaida;
        this.caminhoArquivo = caminhoArquivo;
        this.tamanhoBytes = tamanhoBytes;
        this.hashSha256 = hashSha256;
        this.createdAt = createdAt;
    }

    // --- Getters e Setters Manuais ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AvaliacaoNeuro getAvaliacaoNeuro() {
        return avaliacaoNeuro;
    }

    public void setAvaliacaoNeuro(AvaliacaoNeuro avaliacaoNeuro) {
        this.avaliacaoNeuro = avaliacaoNeuro;
    }

    public String getNomeDoc() {
        return nomeDoc;
    }

    public void setNomeDoc(String nomeDoc) {
        this.nomeDoc = nomeDoc;
    }

    public String getExtensaoDoc() {
        return extensaoDoc;
    }

    public void setExtensaoDoc(String extensaoDoc) {
        this.extensaoDoc = extensaoDoc;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public boolean isEntradaSaida() {
        return entradaSaida;
    }

    public void setEntradaSaida(boolean entradaSaida) {
        this.entradaSaida = entradaSaida;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public Long getTamanhoBytes() {
        return tamanhoBytes;
    }

    public void setTamanhoBytes(Long tamanhoBytes) {
        this.tamanhoBytes = tamanhoBytes;
    }

    public String getHashSha256() {
        return hashSha256;
    }

    public void setHashSha256(String hashSha256) {
        this.hashSha256 = hashSha256;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}