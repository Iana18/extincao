package plataforma.exticao.dtos;

import lombok.Getter;
import lombok.Setter;
import plataforma.exticao.model.*;
import plataforma.exticao.model.Tipo;

@Getter
@Setter
public class SeresRequestDTO {
    private String nomeComum;
    private String nomeCientifico;
    private Tipo tipo;
    private String descricao;
    private StatusConservacao statusConservacao;
    private String imagem;
    private Double latitude;
    private Double longitude;
    private Usuario registradoPor;// ID do usuário que está cadastrando
    private Especie especie;

    private String usuarioLogin;
    private String usuarioEmail;

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
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

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
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
    public Categoria getCategoria() {
        return (especie != null) ? especie.getCategoria() : null;
    }
}