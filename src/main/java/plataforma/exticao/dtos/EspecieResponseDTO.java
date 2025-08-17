package plataforma.exticao.dtos;

public class EspecieResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Long categoriaId; // ID da categoria relacionada

    public EspecieResponseDTO() {}

    public EspecieResponseDTO(Long id, String nome, String descricao, Long categoriaId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoriaId = categoriaId;
    }

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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
