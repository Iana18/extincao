package plataforma.exticao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plataforma.exticao.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {


}
