package plataforma.exticao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plataforma.exticao.model.Comentario;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByEspecieId(Long especieId);
}
