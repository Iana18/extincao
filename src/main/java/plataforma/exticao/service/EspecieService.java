package plataforma.exticao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.exticao.dtos.EspecieResponseDTO;
import plataforma.exticao.dtos.TipoResponseDTO;
import plataforma.exticao.model.Categoria;
import plataforma.exticao.model.Especie;
import plataforma.exticao.model.Tipo;
import plataforma.exticao.repository.CategoriaRepository;
import plataforma.exticao.repository.EspecieRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspecieService {

    @Autowired
    private EspecieRepository especieRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // ------------------------ CREATE ------------------------
    public EspecieResponseDTO registrar(String nome, String descricao, Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Especie especie = new Especie();
        especie.setNome(nome);
        especie.setDescricao(descricao);
        especie.setCategoria(categoria);

        Especie salva = especieRepository.save(especie);
        return toDTO(salva);
    }

    // ------------------------ READ ------------------------
    public List<EspecieResponseDTO> listarTodas() {
        return especieRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<EspecieResponseDTO> buscarPorId(Long id) {
        return especieRepository.findById(id)
                .map(this::toDTO);
    }

    // ------------------------ UPDATE ------------------------
    public EspecieResponseDTO atualizar(Long id, String nome, String descricao, Long categoriaId) {
        Especie especie = especieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        especie.setNome(nome);
        especie.setDescricao(descricao);
        especie.setCategoria(categoria);

        return toDTO(especieRepository.save(especie));
    }

    // ------------------------ DELETE ------------------------
    public void deletar(Long id) {
        Especie especie = especieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));
        especieRepository.delete(especie);
    }

    // ------------------------ MAPPER ------------------------
    private EspecieResponseDTO toDTO(Especie especie) {
        // Converter tipos associados em TipoResponseDTO
        List<TipoResponseDTO> tiposDTO = especie.getTipos() == null ? List.of() :
                especie.getTipos().stream()
                        .map(tipo -> new TipoResponseDTO(
                                tipo.getId(),
                                tipo.getNome(),
                                tipo.getDescricao(),
                                tipo.getEspecie() != null ? tipo.getEspecie().getNome() : "—"
                        ))
                        .toList();

        return new EspecieResponseDTO(
                especie.getId(),
                especie.getNome(),
                especie.getDescricao(),
                especie.getCategoria() != null ? especie.getCategoria().getId() : null,
                especie.getCategoria() != null ? especie.getCategoria().getNome() : null,
                tiposDTO
        );
    }


}
