package plataforma.exticao.dtos;

import jakarta.validation.constraints.*;

public class DenunciaCreateRequestDTO {

    @NotBlank(message = "O título da denúncia é obrigatório.")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 2000, message = "A descrição pode ter no máximo 2000 caracteres.")
    private String descricao;

    private Long especieId;

    @NotBlank(message = "O login do usuário denunciante é obrigatório.")
    private String usuarioLogin;

    @NotBlank(message = "O email do usuário denunciante é obrigatório.")
    @Email(message = "O email deve ser válido.")
    private String usuarioEmail;

    @NotNull(message = "Latitude é obrigatória.")
    private Double latitude;

    @NotNull(message = "Longitude é obrigatória.")
    private Double longitude;

    // ======================
    // Getters e Setters
    // ======================

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getEspecieId() {
        return especieId;
    }

    public void setEspecieId(Long especieId) {
        this.especieId = especieId;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
