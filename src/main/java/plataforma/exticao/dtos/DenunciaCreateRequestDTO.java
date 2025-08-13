package plataforma.exticao.dtos;

import jakarta.validation.constraints.*;

public class DenunciaCreateRequestDTO {

    @NotBlank(message = "O título da denúncia é obrigatório.")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 2000, message = "A descrição pode ter no máximo 2000 caracteres.")
    private String descricao;

    private Long especieId;

    @NotBlank(message = "O ID do usuário denunciante é obrigatório.")
    private String denunciadoPorId;

    @NotNull(message = "Latitude é obrigatória.")
    private Double latitude;

    @NotNull(message = "Longitude é obrigatória.")
    private Double longitude;

    // Getters e Setters

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

    public String getDenunciadoPorId() {
        return denunciadoPorId;
    }

    public void setDenunciadoPorId(String denunciadoPorId) {
        this.denunciadoPorId = denunciadoPorId;
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
