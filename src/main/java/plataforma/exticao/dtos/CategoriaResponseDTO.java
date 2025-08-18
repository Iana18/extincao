package plataforma.exticao.dtos;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

public class CategoriaResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    @JsonManagedReference
    private List<EspecieResponseDTO> especies;

    // Construtores
    public CategoriaResponseDTO() {}

    public CategoriaResponseDTO(Long id, String nome, String descricao, List<EspecieResponseDTO> especies) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.especies = especies;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public List<EspecieResponseDTO> getEspecies() { return especies; }
    public void setEspecies(List<EspecieResponseDTO> especies) { this.especies = especies; }
}
