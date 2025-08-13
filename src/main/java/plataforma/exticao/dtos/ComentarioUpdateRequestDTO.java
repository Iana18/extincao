package plataforma.exticao.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ComentarioUpdateRequestDTO(
        @NotBlank(message = "O texto do comentário não pode ser vazio")
        @Size(max = 500, message = "O comentário não pode ter mais que 500 caracteres")
        String texto
) {}
