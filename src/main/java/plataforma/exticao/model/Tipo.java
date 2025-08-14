package plataforma.exticao.model;

import jakarta.persistence.*;

@Entity
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String nome;
    private  String descricao;

    @ManyToOne
    private  Categoria categoria;
}
