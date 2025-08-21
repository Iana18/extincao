package plataforma.exticao.dtos;

import plataforma.exticao.model.UserRole;

public class UsuarioResponseDTO {

    private String id;
    private String login;
    private String email;
    private String nomeCompleto;
    private UserRole role;

    public UsuarioResponseDTO() {}

    public UsuarioResponseDTO(String id, String login, String email, String nomeCompleto, UserRole role){
        this.id = id;
        this.login = login;
        this.email = email;
        this.nomeCompleto = nomeCompleto;
        this.role = role;
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
}
