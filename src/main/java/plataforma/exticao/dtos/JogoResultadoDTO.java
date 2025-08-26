package plataforma.exticao.dtos;

public class JogoResultadoDTO {
    private Long quizId;
    private int nivelAtual;
    private int acertos;
    private int total;
    private boolean passou;
    private Integer proximoNivel;

    public JogoResultadoDTO(Long quizId, int nivelAtual, int acertos, int total,
                            boolean passou, Integer proximoNivel) {
        this.quizId = quizId;
        this.nivelAtual = nivelAtual;
        this.acertos = acertos;
        this.total = total;
        this.passou = passou;
        this.proximoNivel = proximoNivel;
    }

    // Getters e Setters
    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }

    public int getNivelAtual() { return nivelAtual; }
    public void setNivelAtual(int nivelAtual) { this.nivelAtual = nivelAtual; }

    public int getAcertos() { return acertos; }
    public void setAcertos(int acertos) { this.acertos = acertos; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public boolean isPassou() { return passou; }
    public void setPassou(boolean passou) { this.passou = passou; }

    public Integer getProximoNivel() { return proximoNivel; }
    public void setProximoNivel(Integer proximoNivel) { this.proximoNivel = proximoNivel; }
}
