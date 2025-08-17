package plataforma.exticao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plataforma.exticao.model.Especie;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Long> {
    // você pode adicionar consultas customizadas aqui
}