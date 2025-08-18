package plataforma.exticao.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class TipoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Long especieId; // Para saber a qual Especie pertence
    @JsonBackReference
    private EspecieResponseDTO especie;

    // Construtores
    public TipoResponseDTO() {}

    public TipoResponseDTO(Long id, String nome, String descricao, Long especieId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.especieId = especieId;
    }

    // Getters e Setters
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

    public Long getEspecieId() {
        return especieId;
    }

    public void setEspecieId(Long especieId) {
        this.especieId = especieId;
    }
}
