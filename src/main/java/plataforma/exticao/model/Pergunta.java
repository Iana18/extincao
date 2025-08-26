package plataforma.exticao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonIgnoreProperties ("perguntas") // Ignora apenas a lista de perguntas dentro do quiz
    private Quiz quiz;

    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RespostaQuiz> respostas = new ArrayList<>();


    public Pergunta() {}

    public Pergunta(String texto, Quiz quiz) {
        this.texto = texto;
        this.quiz = quiz;
    }

    // Getters e setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public Quiz getQuiz() { return quiz; }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }
    public List<RespostaQuiz> getRespostas() { return respostas; }
    public void setRespostas(List<RespostaQuiz> respostas) { this.respostas = respostas; }
}
