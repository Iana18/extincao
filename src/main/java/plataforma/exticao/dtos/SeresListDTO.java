package plataforma.exticao.dtos;


import plataforma.exticao.model.Seres;
import plataforma.exticao.model.StatusAprovacao;
import plataforma.exticao.model.StatusConservacao;
import plataforma.exticao.model.Usuario;

import java.time.LocalDateTime;

public class SeresListDTO {
    private Long id;
    private String nomeComum;
    private String nomeCientifico;
    private TipoRequestDTO tipo;
    private EspecieRequestDTO especie;
    private CategoriaRequestDTO categoria;
    private StatusConservacao statusConservacao;
    private StatusAprovacao statusAprovacao;
    private Double latitude;
    private Double longitude;
    private Usuario registradoPor;
    private Usuario aprovadoPor;
    private LocalDateTime dataAprovacao;
    private String descricao;
    private LocalDateTime dataRegistro;


    // Construtor vazio
    public SeresListDTO() {}

    // Construtor a partir de entidade
    public static SeresListDTO fromEntity(Seres s) {
        SeresListDTO dto = new SeresListDTO();
        dto.setId(s.getId());
        dto.setNomeComum(s.getNomeComum());
        dto.setNomeCientifico(s.getNomeCientifico());

        // Criar DTO do tipo
        if (s.getTipo() != null) {
            TipoRequestDTO tipoDto = new TipoRequestDTO();
            tipoDto.setNome(s.getTipo().getNome());

            dto.setTipo(tipoDto);
        }

        // Criar DTO da espécie
        if (s.getEspecie() != null) {
            EspecieRequestDTO especieDto = new EspecieRequestDTO();
            especieDto.setNome(s.getEspecie().getNome());
            especieDto.setId(s.getEspecie().getId());
            dto.setEspecie(especieDto);

            // Categoria da espécie
            if (s.getEspecie().getCategoria() != null) {
                CategoriaRequestDTO categoriaDto = new CategoriaRequestDTO();
                categoriaDto.setNome(s.getEspecie().getCategoria().getNome());

                dto.setCategoria(categoriaDto);
            }
        }

        dto.setStatusConservacao(s.getStatusConservacao());
        dto.setStatusAprovacao(s.getStatusAprovacao());
        dto.setRegistradoPor(s.getRegistradoPor());
        dto.setAprovadoPor(s.getAprovadoPor());
        dto.setLongitude(s.getLongitude());
        dto.setLatitude(s.getLatitude());
        dto.setDescricao(s.getDescricao());
        dto.setDataAprovacao(s.getDataAprovacao());
        dto.setDataRegistro(s.getDataRegistro());

        return dto;
    }



    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeComum() { return nomeComum; }
    public void setNomeComum(String nomeComum) { this.nomeComum = nomeComum; }

    public String getNomeCientifico() { return nomeCientifico; }
    public void setNomeCientifico(String nomeCientifico) { this.nomeCientifico = nomeCientifico; }

    public TipoRequestDTO getTipo() {
        return tipo;
    }

    public void setTipo(TipoRequestDTO tipo) {
        this.tipo = tipo;
    }

    public EspecieRequestDTO getEspecie() {
        return especie;
    }

    public void setEspecie(EspecieRequestDTO especie) {
        this.especie = especie;
    }

    public CategoriaRequestDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaRequestDTO categoria) {
        this.categoria = categoria;
    }

    public StatusConservacao getStatusConservacao() { return statusConservacao; }
    public void setStatusConservacao(StatusConservacao statusConservacao) { this.statusConservacao = statusConservacao; }

    public StatusAprovacao getStatusAprovacao() { return statusAprovacao; }
    public void setStatusAprovacao(StatusAprovacao statusAprovacao) { this.statusAprovacao = statusAprovacao; }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
