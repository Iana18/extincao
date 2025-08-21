package plataforma.exticao.dtos;

import plataforma.exticao.model.StatusAprovacao;

import java.time.LocalDateTime;

public class DenunciaResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String imagem;
    private StatusAprovacao statusAprovacao;
    private LocalDateTime dataDenuncia;
    private LocalDateTime dataAprovacao;
    private LocalDateTime dataResolucao; // novo campo
    private Double latitude;
    private Double longitude;
    private Long especieId;
    private UsuarioResponseDTO denunciadoPor;
    private UsuarioResponseDTO aprovadoPor;

    public DenunciaResponseDTO() {}

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }

    public StatusAprovacao getStatusAprovacao() { return statusAprovacao; }
    public void setStatusAprovacao(StatusAprovacao statusAprovacao) { this.statusAprovacao = statusAprovacao; }

    public LocalDateTime getDataDenuncia() { return dataDenuncia; }
    public void setDataDenuncia(LocalDateTime dataDenuncia) { this.dataDenuncia = dataDenuncia; }

    public LocalDateTime getDataAprovacao() { return dataAprovacao; }
    public void setDataAprovacao(LocalDateTime dataAprovacao) { this.dataAprovacao = dataAprovacao; }

    public LocalDateTime getDataResolucao() { return dataResolucao; }
    public void setDataResolucao(LocalDateTime dataResolucao) { this.dataResolucao = dataResolucao; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Long getEspecieId() { return especieId; }
    public void setEspecieId(Long especieId) { this.especieId = especieId; }

    public UsuarioResponseDTO getDenunciadoPor() { return denunciadoPor; }
    public void setDenunciadoPor(UsuarioResponseDTO denunciadoPor) { this.denunciadoPor = denunciadoPor; }

    public UsuarioResponseDTO getAprovadoPor() { return aprovadoPor; }
    public void setAprovadoPor(UsuarioResponseDTO aprovadoPor) { this.aprovadoPor = aprovadoPor; }
}
