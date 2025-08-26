package plataforma.exticao.dtos;

import plataforma.exticao.model.Seres;

public class SeresDetailDTO extends SeresListDTO {
    private String imagem; // Base64

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }

    public static SeresListDTO fromEntity(Seres s) {
        SeresListDTO dto = new SeresListDTO();
        dto.setId(s.getId());
        dto.setNomeComum(s.getNomeComum());
        dto.setNomeCientifico(s.getNomeCientifico());

        // Criar DTO do tipo
        if (s.getTipo() != null) {
            TipoRequestDTO tipoDto = new TipoRequestDTO();
            tipoDto.setNome(s.getTipo().getNome());
            dto.setTipo(tipoDto);
        }

        // Criar DTO da espécie
        if (s.getEspecie() != null) {
            EspecieRequestDTO especieDto = new EspecieRequestDTO();
            especieDto.setNome(s.getEspecie().getNome());
            especieDto.setId(s.getEspecie().getId());
            dto.setEspecie(especieDto);

            // Categoria da espécie
            if (s.getEspecie().getCategoria() != null) {
                CategoriaRequestDTO categoriaDto = new CategoriaRequestDTO();
                categoriaDto.setNome(s.getEspecie().getCategoria().getNome());

                dto.setCategoria(categoriaDto);
            }
        }

        dto.setStatusConservacao(s.getStatusConservacao());
        dto.setStatusAprovacao(s.getStatusAprovacao());
        dto.setRegistradoPor(s.getRegistradoPor());
        dto.setAprovadoPor(s.getAprovadoPor());
        dto.setLongitude(s.getLongitude());
        dto.setLatitude(s.getLatitude());
        dto.setDescricao(s.getDescricao());
        dto.setDataAprovacao(s.getDataAprovacao());
        dto.setDataRegistro(s.getDataRegistro());

        return dto;
    }


}