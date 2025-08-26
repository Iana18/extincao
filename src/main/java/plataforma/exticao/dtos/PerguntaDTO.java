package plataforma.exticao.dtos;

import plataforma.exticao.model.Quiz;
import plataforma.exticao.model.RespostaQuiz;

import java.util.List;

public class PerguntaDTO {
    private Long id;
    private String texto;
    private Quiz quiz; // aqui o Quiz será incluído
    private List<RespostaQuiz> respostas;


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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<RespostaQuiz> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<RespostaQuiz> respostas) {
        this.respostas = respostas;
    }


    // getters e setters
}