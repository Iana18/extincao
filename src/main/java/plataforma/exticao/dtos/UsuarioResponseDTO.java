package plataforma.exticao.dtos;

import plataforma.exticao.model.Quiz;
import plataforma.exticao.model.UserRole;

import java.util.Set;

public class UsuarioResponseDTO {

    private String id;
    private String login;
    private String email;
    private String nomeCompleto;
    private UserRole role;
    private int nivelAtual; // progresso do quiz
    private Set<Quiz> quizzesCompletos; // quizzes finalizados


    public UsuarioResponseDTO() {}

    public UsuarioResponseDTO(String id, String login, String email, String nomeCompleto, UserRole role,
                              int nivelAtual,Set<Quiz> quizzesCompletos){
        this.id = id;
        this.login = login;
        this.email = email;
        this.nomeCompleto = nomeCompleto;
        this.role = role;
        this.nivelAtual = nivelAtual;
        this.quizzesCompletos = quizzesCompletos;
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public int getNivelAtual() {
        return nivelAtual;
    }

    public void setNivelAtual(int nivelAtual) {
        this.nivelAtual = nivelAtual;
    }

    public Set<Quiz> getQuizzesCompletos() {
        return quizzesCompletos;
    }

    public void setQuizzesCompletos(Set<Quiz> quizzesCompletos) {
        this.quizzesCompletos = quizzesCompletos;
    }
}
