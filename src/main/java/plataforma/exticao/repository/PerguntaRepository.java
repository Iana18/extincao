package plataforma.exticao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plataforma.exticao.model.Pergunta;

import java.util.List;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
    List<Pergunta> findByQuizId(Long quizId);
}
