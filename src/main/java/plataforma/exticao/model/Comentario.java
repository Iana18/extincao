package plataforma.exticao.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String texto;

    private LocalDateTime dataComentario;

    @ManyToOne
    private Usuario autor;

    @ManyToOne
    private Seres especie;

    public Comentario() {}

    public Comentario(String texto, LocalDateTime dataComentario, Usuario autor, Seres especie) {
        this.texto = texto;
        this.dataComentario = dataComentario;
        this.autor = autor;
        this.especie = especie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(LocalDateTime dataComentario) {
        this.dataComentario = dataComentario;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Seres getEspecie() {
        return especie;
    }

    public void setEspecie(Seres especie) {
        this.especie = especie;
    }
}