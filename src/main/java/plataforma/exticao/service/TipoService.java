package plataforma.exticao.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import plataforma.exticao.model.Tipo;
import plataforma.exticao.repository.TipoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    public List<Tipo> findAll() {
        return tipoRepository.findAll();
    }

    public Optional<Tipo> findById(Long id) {
        return tipoRepository.findById(id);
    }

    public Tipo save(Tipo tipo) {
        return tipoRepository.save(tipo);
    }

    public Tipo update(Long id, Tipo tipo) {
        return tipoRepository.findById(id).map(t -> {
            t.setNome(tipo.getNome());
            return tipoRepository.save(t);
        }).orElseThrow(() -> new RuntimeException("Tipo não encontrado"));
    }

    public void delete(Long id) {
        if(tipoRepository.existsById(id)){
            tipoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Tipo não encontrado");
        }
    }
}
