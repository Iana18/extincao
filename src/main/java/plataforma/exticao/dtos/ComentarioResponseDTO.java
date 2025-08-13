package plataforma.exticao.dtos;

import java.time.LocalDateTime;

public class ComentarioResponseDTO {

    private Long id;
    private String texto;
    private LocalDateTime dataComentario;
    private Long especieId;
    private String usuarioId;

    public ComentarioResponseDTO(Long id, String texto, LocalDateTime dataComentario, Long especieId, String usuarioId) {
        this.id = id;
        this.texto = texto;
        this.dataComentario = dataComentario;
        this.especieId = especieId;
        this.usuarioId = usuarioId;
    }

    // getters

    public Long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDateTime getDataComentario() {
        return dataComentario;
    }

    public Long getEspecieId() {
        return especieId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }
}
