package plataforma.exticao.dtos;

import jakarta.validation.constraints.NotNull;
import plataforma.exticao.model.StatusAprovacao;

public class DenunciaUpdateStatusDTO {
    @NotNull(message = "O status da denúncia é obrigatório.")
    private StatusAprovacao statusAprovacao;

    public DenunciaUpdateStatusDTO() {}

    public DenunciaUpdateStatusDTO(StatusAprovacao statusAprovacao) {
        this.statusAprovacao = statusAprovacao;
    }

    public StatusAprovacao getStatusAprovacao() {
        return statusAprovacao;
    }

    public void setStatusAprovacao(StatusAprovacao statusAprovacao) {
        this.statusAprovacao = statusAprovacao;
    }
}
