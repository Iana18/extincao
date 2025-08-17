package plataforma.exticao.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import plataforma.exticao.dtos.SeresRequestDTO;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
public class Seres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeComum;
    private String nomeCientifico;

    @ManyToOne
    private  Especie especie;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusConservacao statusConservacao;

    @ManyToOne
    private Tipo tipo;  // Referência ao tipo (Terrestre, Aquático, etc.)

    @ManyToOne
    private Categoria categoria; // Referência à categoria (Animal, Planta, Outro)

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
    @OneToMany(mappedBy = "especie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Denuncia> denuncias;

    public Seres() {}

    public Seres(String nomeComum, String nomeCientifico, String descricao,
                 StatusConservacao statusConservacao, String imagem, LocalDateTime dataRegistro,
                 StatusAprovacao statusAprovacao, LocalDateTime dataAprovacao,
                 Usuario registradoPor, Usuario aprovadoPor, Double latitude, Double longitude,
                 List<Denuncia> denuncias, Tipo tipo,Categoria categoria ) {
        this.nomeComum = nomeComum;
        this.nomeCientifico = nomeCientifico;

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
        this.denuncias = denuncias;
        this.tipo = tipo;
        this.categoria = categoria;
    }

    public  Seres(SeresRequestDTO seres){
        this.nomeComum = seres.getNomeComum();
        this.nomeCientifico = seres.getNomeCientifico();
        this.especie=  seres.getEspecie();
        this.descricao = seres.getDescricao();
        this.statusConservacao = seres.getStatusConservacao();
        this.imagem = seres.getImagem();
        this.dataRegistro = LocalDateTime.now();
        this.statusAprovacao = StatusAprovacao.PENDENTE;
        this.registradoPor = getRegistradoPor();
        this.latitude = seres.getLatitude();
        this.longitude = seres.getLongitude();
        this.tipo = seres.getTipo();
        this.categoria=  seres.getCategoria();
    }

    public Seres(SeresRequestDTO dto, Usuario registradoPor) {
        this.nomeComum = dto.getNomeComum();
        this.nomeCientifico = dto.getNomeCientifico();
        this.especie = dto.getEspecie();
        this.descricao = dto.getDescricao();
        this.statusConservacao = dto.getStatusConservacao();
        this.imagem = dto.getImagem();
        this.dataRegistro = LocalDateTime.now();
        this.statusAprovacao = StatusAprovacao.PENDENTE;
        this.registradoPor = registradoPor; // agora vem do parâmetro
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.tipo = dto.getTipo();
        this.categoria = dto.getCategoria();
    }

    public Seres(SeresRequestDTO dto, Usuario registradoPor, Long id) {
        this(dto, registradoPor); // chama o outro construtor
        this.id = id; // atribui o ID depois
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

    public List<Denuncia> getDenuncias() {
        return denuncias;
    }

    public void setDenuncias(List<Denuncia> denuncias) {
        this.denuncias = denuncias;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
