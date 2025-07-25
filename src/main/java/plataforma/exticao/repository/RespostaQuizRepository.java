package plataforma.exticao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plataforma.exticao.model.RespostaQuiz;

import java.util.List;

public interface RespostaQuizRepository extends JpaRepository<RespostaQuiz, Long> {
    List<RespostaQuiz> findByPerguntaId(Long perguntaId);
}
