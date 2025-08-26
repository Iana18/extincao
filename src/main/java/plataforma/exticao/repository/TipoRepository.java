package plataforma.exticao.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plataforma.exticao.model.Tipo;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long> {

    //Optional<Tipo> findByNome(String nome); // busca um único tipo por nome

    List<Tipo> findByNomeInIgnoreCase(List<String> nomes); // busca múltiplos tipos pelo nome

    @Override
    List<Tipo> findAll(Sort sort);
}
