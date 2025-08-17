package plataforma.exticao.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import plataforma.exticao.model.Especie;
import plataforma.exticao.repository.EspecieRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EspecieService {

    @Autowired
    private EspecieRepository especieRepository;

    public List<Especie> findAll() {
        return especieRepository.findAll();
    }

    public Optional<Especie> findById(Long id) {
        return especieRepository.findById(id);
    }

    public Especie save(Especie especie) {
        return especieRepository.save(especie);
    }

    public Especie update(Long id, Especie especie) {
        return especieRepository.findById(id).map(e -> {
            e.setNome(especie.getNome());
            e.setCategoria(especie.getCategoria());
            e.setTipos(especie.getTipos());
            return especieRepository.save(e);
        }).orElseThrow(() -> new RuntimeException("Espécie não encontrada"));
    }

    public void delete(Long id) {
        if(especieRepository.existsById(id)){
            especieRepository.deleteById(id);
        } else {
            throw new RuntimeException("Espécie não encontrada");
        }
    }
}
