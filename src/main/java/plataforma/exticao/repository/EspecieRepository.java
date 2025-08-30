package plataforma.exticao.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plataforma.exticao.model.Especie;
import plataforma.exticao.model.Seres;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Long> {

   // Optional<Especie> findByNome(String nome); // busca única

    // Buscar todas as espécies cujo nome está em uma lista (ignorando case)
    List<Especie> findByNomeInIgnoreCase(List<String> nomes);

    // Buscar por nome exato (ignorando case)
    Optional<Especie> findByNomeIgnoreCase(String nome);

    @EntityGraph(attributePaths = {"categoria", "tipos"})
    List<Especie> findAll();
}
