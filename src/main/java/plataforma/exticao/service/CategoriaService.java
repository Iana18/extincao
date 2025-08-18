package plataforma.exticao.service;

import org.springframework.stereotype.Service;
import plataforma.exticao.model.Categoria;
import plataforma.exticao.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Listar todas as categorias
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    // Buscar categoria por ID
    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }

    // Buscar categoria por nome
    public Optional<Categoria> findByNome(String nome) {
        return categoriaRepository.findByNome(nome);
    }

    // Criar nova categoria
    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // Atualizar categoria
    public Categoria atualizar(Long id, Categoria categoriaAtualizada) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + id));

        existente.setNome(categoriaAtualizada.getNome());
        existente.setDescricao(categoriaAtualizada.getDescricao());

        return categoriaRepository.save(existente);
    }

    // Deletar categoria
    public void deletar(Long id) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + id));

        categoriaRepository.delete(existente);
    }
}
