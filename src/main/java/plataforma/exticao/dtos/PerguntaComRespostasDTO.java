package plataforma.exticao.dtos;

import java.util.List;

public class PerguntaComRespostasDTO {
    private Long quizId;
    private String texto;
    private List<RespostaDTO> respostas;


    public List<RespostaDTO> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<RespostaDTO> respostas) {
        this.respostas = respostas;
    }

    public String getTexto() {
        return texto;
    }


    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
}

