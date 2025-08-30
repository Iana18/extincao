package plataforma.exticao.dtos;


import lombok.Getter;
import lombok.Setter;
import plataforma.exticao.model.Especie;
import plataforma.exticao.model.Seres;
import plataforma.exticao.model.Tipo;


public class DenunciaUpdateRequestDTO {
    private String titulo;
    private String descricao;
    private Especie especie;
    private String usuarioLogin;
    private String usuarioEmail;

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public Especie getEspecie() { return especie; }
    public void setEspecie(Especie especie) { this.especie = especie; }

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


}