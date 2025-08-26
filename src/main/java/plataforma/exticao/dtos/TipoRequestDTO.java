package plataforma.exticao.dtos;
public class TipoRequestDTO {
    private String nome;
    private String descricao;
    private Long especieId; // apenas o id da especie escolhida
    // getters e setters

    public TipoRequestDTO() {}

    public TipoRequestDTO(String nome, String descricao, Long especieI) {
        this.nome = nome;
        this.descricao = descricao;
        this.especieId = especieId;

    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Long getEspecieId() { return especieId; }
    public void setEspecieId(Long especieId) { this.especieId = especieId; }


}
