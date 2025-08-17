package plataforma.exticao.dtos;

import lombok.Getter;
import lombok.Setter;
import plataforma.exticao.model.Especie;
import plataforma.exticao.model.StatusConservacao;
import plataforma.exticao.model.TipoEspecie;
import plataforma.exticao.model.Usuario;

@Getter
@Setter
public class SeresRequestDTO {
    private String nomeComum;
    private String nomeCientifico;
    private TipoEspecie tipo;
    private String descricao;
    private StatusConservacao statusConservacao;
    private String imagem;
    private Double latitude;
    private Double longitude;
    private Usuario registradoPor;
    private Especie especie;// ID do usuário que está cadastrando


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

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }
}