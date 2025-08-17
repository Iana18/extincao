package plataforma.exticao.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Tipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;        // Ex: Terrestre, Aquático, Arbóreo
    private String descricao;

    @ManyToOne
    private Especie especie;

    @OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seres> seres;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public List<Seres> getSeres() {
        return seres;
    }

    public void setSeres(List<Seres> seres) {
        this.seres = seres;
    }
}
