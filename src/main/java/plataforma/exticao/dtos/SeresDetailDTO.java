package plataforma.exticao.dtos;

import plataforma.exticao.model.Seres;

public class SeresDetailDTO extends SeresListDTO {
    private String imagem; // Base64

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }

    public static SeresDetailDTO fromEntity(Seres s) {
        SeresDetailDTO dto = new SeresDetailDTO(); // agora é do tipo correto

        dto.setId(s.getId());
        dto.setNomeComum(s.getNomeComum());
        dto.setNomeCientifico(s.getNomeCientifico());
        dto.setDescricao(s.getDescricao());
        dto.setStatusConservacao(s.getStatusConservacao());
        dto.setStatusAprovacao(s.getStatusAprovacao());
        dto.setLatitude(s.getLatitude());
        dto.setLongitude(s.getLongitude());
        dto.setImagem(s.getImagem()); // imagem Base64
        dto.setRegistradoPor(s.getRegistradoPor());
        dto.setAprovadoPor(s.getAprovadoPor());

        if (s.getTipo() != null) {
            TipoRequestDTO tipoDto = new TipoRequestDTO();
            tipoDto.setNome(s.getTipo().getNome());
            dto.setTipo(tipoDto);
        }

        if (s.getEspecie() != null) {
            EspecieRequestDTO especieDto = new EspecieRequestDTO();
            especieDto.setId(s.getEspecie().getId());
            especieDto.setNome(s.getEspecie().getNome());
            dto.setEspecie(especieDto);

            if (s.getEspecie().getCategoria() != null) {
                CategoriaRequestDTO categoriaDto = new CategoriaRequestDTO();
                categoriaDto.setNome(s.getEspecie().getCategoria().getNome());
                dto.setCategoria(categoriaDto);
            }
        }

        dto.setDataRegistro(s.getDataRegistro());
        dto.setDataAprovacao(s.getDataAprovacao());

        return dto;
    }

}