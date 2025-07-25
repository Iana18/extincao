package plataforma.exticao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class RespostaQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    private boolean correta;

    @ManyToOne
    @JoinColumn(name = "pergunta_id")
    @JsonBackReference
    private Pergunta pergunta;


    public RespostaQuiz() {}

    public RespostaQuiz(String texto, boolean correta, Pergunta pergunta) {
        this.texto = texto;
        this.correta = correta;
        this.pergunta = pergunta;
    }

    // Getters e setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public boolean isCorreta() { return correta; }
    public void setCorreta(boolean correta) { this.correta = correta; }
    public Pergunta getPergunta() { return pergunta; }
    public void setPergunta(Pergunta pergunta) { this.pergunta = pergunta; }
}
