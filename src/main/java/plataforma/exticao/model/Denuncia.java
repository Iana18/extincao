package plataforma.exticao.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
     private Usuario denunciadoPor;
    private String titulo;

    @Column(length = 2000)
    private String descricao;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imagem;
    @Enumerated(EnumType.STRING)
    private StatusAprovacao statusAprovacao;

    private LocalDateTime dataDenuncia;

    private LocalDateTime dataAprovacao;
    @ManyToOne
    private Usuario aprovadoPor;

    private Double latitude;
    private Double longitude;


    @ManyToOne
    private Especie especie;

    public Denuncia() {}

    public Denuncia(String titulo, String descricao, StatusAprovacao statusAprovacao, LocalDateTime dataDenuncia,
                    LocalDateTime dataAprovacao, Usuario denunciadoPor, Usuario aprovadoPor, Especie especie, String imagem) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.statusAprovacao = statusAprovacao;
        this.dataDenuncia = dataDenuncia;
        this.dataAprovacao = dataAprovacao;
        this.denunciadoPor = denunciadoPor;
        this.aprovadoPor = aprovadoPor;
        this.especie = especie;
        this.imagem= imagem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusAprovacao getStatusAprovacao() {
        return statusAprovacao;
    }

    public void setStatusAprovacao(StatusAprovacao statusAprovacao) {
        this.statusAprovacao = statusAprovacao;
    }

    public LocalDateTime getDataDenuncia() {
        return dataDenuncia;
    }

    public void setDataDenuncia(LocalDateTime dataDenuncia) {
        this.dataDenuncia = dataDenuncia;
    }

    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    public Usuario getDenunciadoPor() {
        return denunciadoPor;
    }

    public void setDenunciadoPor(Usuario denunciadoPor) {
        this.denunciadoPor = denunciadoPor;
    }

    public Usuario getAprovadoPor() {
        return aprovadoPor;
    }

    public void setAprovadoPor(Usuario aprovadoPor) {
        this.aprovadoPor = aprovadoPor;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }


    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

}
