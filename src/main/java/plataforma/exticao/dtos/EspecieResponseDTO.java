package plataforma.exticao.dtos;

import lombok.Getter;
import lombok.Setter;
import plataforma.exticao.model.StatusAprovacao;
import plataforma.exticao.model.StatusConservacao;
import plataforma.exticao.model.TipoEspecie;

import java.time.LocalDateTime;

@Getter
@Setter
public class EspecieResponseDTO {
    private Long id;
    private String nomeComum;
    private String nomeCientifico;
    private TipoEspecie tipo;
    private String descricao;
    private StatusConservacao statusConservacao;
    private String imagem;
    private Double latitude;
    private Double longitude;
    private StatusAprovacao statusAprovacao;
    private LocalDateTime dataRegistro;
    private LocalDateTime dataAprovacao;
    private String registradoPorLogin;
    private String aprovadoPorLogin;
}
