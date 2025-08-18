package plataforma.exticao.service;

import org.springframework.stereotype.Service;
import plataforma.exticao.dtos.*;
import plataforma.exticao.model.Categoria;
import plataforma.exticao.model.Especie;
import plataforma.exticao.model.Tipo;
import plataforma.exticao.repository.CategoriaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Converter Tipo -> TipoResponseDTO
    private TipoResponseDTO tipoToDTO(Tipo tipo) {
        return new TipoResponseDTO(
                tipo.getId(),
                tipo.getNome(),
                tipo.getDescricao(),
                tipo.getEspecie() != null ? tipo.getEspecie().getId() : null
        );
    }

    // Converter Especie -> EspecieResponseDTO
    private EspecieResponseDTO especieToDTO(Especie especie) {
        List<TipoResponseDTO> tiposDTO = especie.getTipos() == null ? List.of() :
                especie.getTipos().stream()
                        .map(this::tipoToDTO)
                        .collect(Collectors.toList());

        return new EspecieResponseDTO(
                especie.getId(),
                especie.getNome(),
                especie.getDescricao(),
                especie.getCategoria() != null ? especie.getCategoria().getId() : null,
                especie.getCategoria() != null ? especie.getCategoria().getNome() : null, // novo campo
                tiposDTO
        );
    }

    // Converter Categoria -> CategoriaResponseDTO
    private CategoriaResponseDTO categoriaToDTO(Categoria categoria) {
        List<EspecieResponseDTO> especiesDTO = categoria.getEspecies() == null ? List.of() :
                categoria.getEspecies().stream()
                        .map(this::especieToDTO)
                        .collect(Collectors.toList());

        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao(),
                especiesDTO
        );
    }

    // Listar todas as categorias
    public List<CategoriaResponseDTO> findAll() {
        return categoriaRepository.findAll().stream()
                .map(this::categoriaToDTO)
                .collect(Collectors.toList());
    }

    // Buscar categoria por ID
    public CategoriaResponseDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + id));
        return categoriaToDTO(categoria);
    }

    // Criar nova categoria
    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        Categoria salva = categoriaRepository.save(categoria);
        return categoriaToDTO(salva);
    }

    // Atualizar categoria
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + id));

        existente.setNome(dto.getNome());
        existente.setDescricao(dto.getDescricao());

        Categoria atualizada = categoriaRepository.save(existente);
        return categoriaToDTO(atualizada);
    }

    // Deletar categoria
    public void deletar(Long id) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + id));

        categoriaRepository.delete(existente);
    }
}
