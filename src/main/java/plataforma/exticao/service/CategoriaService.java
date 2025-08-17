package plataforma.exticao.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import plataforma.exticao.model.Categoria;
import plataforma.exticao.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Long id, Categoria categoria) {
        return categoriaRepository.findById(id).map(c -> {
            c.setNome(categoria.getNome());
            return categoriaRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public void delete(Long id) {
        if(categoriaRepository.existsById(id)){
            categoriaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Categoria não encontrada");
        }
    }
}
