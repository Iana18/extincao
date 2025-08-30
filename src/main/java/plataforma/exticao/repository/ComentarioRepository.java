package plataforma.exticao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plataforma.exticao.dtos.ComentarioResponseDTO;
import plataforma.exticao.model.Comentario;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByEspecieId(Long especieId);
    // Busca todos os comentários de uma espécie, ordenados por data de comentário crescente
    List<Comentario> findByEspecieIdOrderByDataComentarioAsc(Long especieId);
}
