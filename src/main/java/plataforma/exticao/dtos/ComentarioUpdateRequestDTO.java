package plataforma.exticao.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ComentarioUpdateRequestDTO {

    @NotBlank(message = "O texto do comentário é obrigatório.")
    @Size(max = 2000, message = "O texto pode ter no máximo 1000 caracteres.")
    private String texto;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}