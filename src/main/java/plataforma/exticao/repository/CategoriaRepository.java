package plataforma.exticao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plataforma.exticao.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
}
