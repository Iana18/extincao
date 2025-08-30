package plataforma.exticao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 2000)
    private String descricao;

    // Relacionamento direto com a espécie denunciada
    @ManyToOne
    @JoinColumn(name = "especie_id") // FK para a tabela Especie
    @JsonBackReference
    private Especie especie;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imagem;

    @Enumerated(EnumType.STRING)
    private StatusAprovacao statusAprovacao;

    private LocalDateTime dataDenuncia;

    private LocalDateTime dataAprovacao;

    private LocalDateTime dataResolucao; // quando mudar para RESOLVIDA

    @ManyToOne
    private Usuario denunciadoPor;

    @ManyToOne
    private Usuario aprovadoPor;

    private Double latitude;
    private Double longitude;

    // 🔹 Construtor padrão já inicializa status e data
    public Denuncia() {
        this.statusAprovacao = StatusAprovacao.PENDENTE;
        this.dataDenuncia = LocalDateTime.now();
    }

    // 🔹 Construtor completo
    public Denuncia(String titulo, String descricao, StatusAprovacao statusAprovacao,
                    LocalDateTime dataDenuncia, LocalDateTime dataAprovacao,
                    Usuario denunciadoPor, Usuario aprovadoPor, Especie especie,
                    String imagem, Double latitude, Double longitude) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.statusAprovacao = statusAprovacao != null ? statusAprovacao : StatusAprovacao.PENDENTE;
        this.dataDenuncia = dataDenuncia != null ? dataDenuncia : LocalDateTime.now();
        this.dataAprovacao = dataAprovacao;
        this.denunciadoPor = denunciadoPor;
        this.aprovadoPor = aprovadoPor;
        this.especie = especie;
        this.imagem = imagem;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // ======================
    // Getters e Setters
    // ======================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Especie getEspecie() { return especie; }
    public void setEspecie(Especie especie) { this.especie = especie; }

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

    public Usuario getDenunciadoPor() { return denunciadoPor; }
    public void setDenunciadoPor(Usuario denunciadoPor) { this.denunciadoPor = denunciadoPor; }

    public Usuario getAprovadoPor() { return aprovadoPor; }
    public void setAprovadoPor(Usuario aprovadoPor) { this.aprovadoPor = aprovadoPor; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
