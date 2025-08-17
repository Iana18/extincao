package plataforma.exticao.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plataforma.exticao.model.Seres;
import plataforma.exticao.model.StatusAprovacao;
import plataforma.exticao.model.StatusConservacao;
import plataforma.exticao.model.TipoEspecie;

import java.util.List;

@Repository
public interface SeresRepository extends JpaRepository<Seres, Long> {

        List<Seres> findByStatusAprovacao(StatusAprovacao status);

        List<Seres> findByNomeComumContainingIgnoreCase(String nome);

        List<Seres> findByTipo(TipoEspecie tipo);

        List<Seres> findByStatusConservacao(StatusConservacao statusConservacao);

        List<Seres> findByNomeComumContainingIgnoreCaseAndTipoAndStatusConservacao(
                String nomeComum, TipoEspecie tipo, StatusConservacao status);

        List<Seres> findByNomeComumContainingIgnoreCaseAndTipo(String nomeComum, TipoEspecie tipo);

        List<Seres> findByNomeComumContainingIgnoreCaseAndStatusConservacao(
                String nomeComum, StatusConservacao status);

        List<Seres> findByTipoAndStatusConservacao(TipoEspecie tipo, StatusConservacao status);

        // Caso queira listar tudo com ordenação:
        List<Seres> findAll(Sort sort);
}
