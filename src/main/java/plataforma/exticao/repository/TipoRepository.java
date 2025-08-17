package plataforma.exticao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plataforma.exticao.model.Tipo;

public interface TipoRepository extends JpaRepository<Tipo, Long> {
}
