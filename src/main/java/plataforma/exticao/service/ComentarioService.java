package plataforma.exticao.service;
import org.springframework.stereotype.Service;
import plataforma.exticao.model.Comentario;
import plataforma.exticao.repository.ComentarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    public Comentario salvar(Comentario comentario) {
        comentario.setDataComentario(LocalDateTime.now());
        return comentarioRepository.save(comentario);
    }

    public List<Comentario> listarPorEspecie(Long especieId) {
        return comentarioRepository.findByEspecieId(especieId);
    }

    public Optional<Comentario> buscarPorId(Long id) {
        return comentarioRepository.findById(id);
    }

    public void deletar(Long id) {
        comentarioRepository.deleteById(id);
    }
}