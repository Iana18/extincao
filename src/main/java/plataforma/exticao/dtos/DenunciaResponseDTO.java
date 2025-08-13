package plataforma.exticao.dtos;

import java.time.LocalDateTime;
import plataforma.exticao.model.StatusAprovacao;

public class DenunciaResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private StatusAprovacao statusAprovacao;
    private LocalDateTime dataDenuncia;
    private LocalDateTime dataAprovacao;
    private String denunciadoPorId;
    private String aprovadoPorId;
    private Long especieId;
    private String imagem;
    private Double latitude;
    private Double longitude;

    public DenunciaResponseDTO(Long id, String titulo, String descricao, StatusAprovacao statusAprovacao,
                               LocalDateTime dataDenuncia, LocalDateTime dataAprovacao,
                               String denunciadoPorId, String aprovadoPorId,
                               Long especieId, String imagem, Double latitude, Double longitude) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.statusAprovacao = statusAprovacao;
        this.dataDenuncia = dataDenuncia;
        this.dataAprovacao = dataAprovacao;
        this.denunciadoPorId = denunciadoPorId;
        this.aprovadoPorId = aprovadoPorId;
        this.especieId = especieId;
        this.imagem = imagem;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters (omiti setters para ser imutável)
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public StatusAprovacao getStatusAprovacao() { return statusAprovacao; }
    public LocalDateTime getDataDenuncia() { return dataDenuncia; }
    public LocalDateTime getDataAprovacao() { return dataAprovacao; }
    public String getDenunciadoPorId() { return denunciadoPorId; }
    public String getAprovadoPorId() { return aprovadoPorId; }
    public Long getEspecieId() { return especieId; }
    public String getImagem() { return imagem; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
}
