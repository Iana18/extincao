package plataforma.exticao.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class Especie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeComum;
    private String nomeCientifico;

    @Enumerated(EnumType.STRING)
    private TipoEspecie tipo;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusConservacao statusConservacao;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imagem;

    private LocalDateTime dataRegistro;
  
    @Enumerated(EnumType.STRING)
    private StatusAprovacao statusAprovacao;

    private LocalDateTime dataAprovacao;

    @ManyToOne
    private Usuario registradoPor;

    @ManyToOne
    private Usuario aprovadoPor;

    private Double latitude;
    private Double longitude;

    public Especie() {}

    public Especie(String nomeComum, String nomeCientifico, TipoEspecie tipo, String descricao,
                   StatusConservacao statusConservacao, String imagem, LocalDateTime dataRegistro,
                   StatusAprovacao statusAprovacao, LocalDateTime dataAprovacao,
                   Usuario registradoPor, Usuario aprovadoPor, Double latitude, Double longitude) {
        this.nomeComum = nomeComum;
        this.nomeCientifico = nomeCientifico;
        this.tipo = tipo;
        this.descricao = descricao;
        this.statusConservacao = statusConservacao;
        this.imagem = imagem;
        this.dataRegistro = dataRegistro;
        this.statusAprovacao = statusAprovacao;
        this.dataAprovacao = dataAprovacao;
        this.registradoPor = registradoPor;
        this.aprovadoPor = aprovadoPor;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeComum() {
        return nomeComum;
    }

    public void setNomeComum(String nomeComum) {
        this.nomeComum = nomeComum;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    public TipoEspecie getTipo() {
        return tipo;
    }

    public void setTipo(TipoEspecie tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusConservacao getStatusConservacao() {
        return statusConservacao;
    }

    public void setStatusConservacao(StatusConservacao statusConservacao) {
        this.statusConservacao = statusConservacao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public StatusAprovacao getStatusAprovacao() {
        return statusAprovacao;
    }

    public void setStatusAprovacao(StatusAprovacao statusAprovacao) {
        this.statusAprovacao = statusAprovacao;
    }

    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    public Usuario getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(Usuario registradoPor) {
        this.registradoPor = registradoPor;
    }

    public Usuario getAprovadoPor() {
        return aprovadoPor;
    }

    public void setAprovadoPor(Usuario aprovadoPor) {
        this.aprovadoPor = aprovadoPor;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
