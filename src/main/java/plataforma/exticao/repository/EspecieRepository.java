package plataforma.exticao.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plataforma.exticao.model.Especie;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Long> {

    Optional<Especie> findByNome(String nome);

    // Busca todas as espécies já carregando categoria e tipos
    @EntityGraph (attributePaths = {"categoria", "tipos"})
    List<Especie> findAll();
}