package plataforma.exticao.dtos;

public class TipoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String especieNome; // só o nome da especie

    public TipoResponseDTO() {}

    public TipoResponseDTO(Long id, String nome, String descricao, String especieNome) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.especieNome = especieNome;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getEspecieNome() {
        return especieNome;
    }

    public void setEspecieNome(String especieNome) {
        this.especieNome = especieNome;
    }
}
