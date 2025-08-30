package plataforma.exticao.dtos;

import java.time.LocalDateTime;

public class ComentarioResponseDTO {

    private Long id;
    private String texto;
    private LocalDateTime dataComentario;
    private Long especieId;
    private String autorLogin; // apenas o login

    public ComentarioResponseDTO(Long id, String texto, LocalDateTime dataComentario, Long especieId, String autorLogin) {
        this.id = id;
        this.texto = texto;
        this.dataComentario = dataComentario;
        this.especieId = especieId;
        this.autorLogin = autorLogin;
    }

    // getters
    public Long getId() { return id; }
    public String getTexto() { return texto; }
    public LocalDateTime getDataComentario() { return dataComentario; }
    public Long getEspecieId() { return especieId; }
    public String getAutorLogin() { return autorLogin; }
}
