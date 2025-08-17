package plataforma.exticao.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plataforma.exticao.model.*;

import java.util.List;

@Repository
public interface SeresRepository extends JpaRepository<Seres, Long> {

        List<Seres> findByStatusAprovacao(StatusAprovacao status);

        List<Seres> findByNomeComumContainingIgnoreCase(String nome);

        List<Seres> findByEspecie(Especie especie);

        List<Seres> findByStatusConservacao(StatusConservacao statusConservacao);

        List<Seres> findByNomeComumContainingIgnoreCaseAndEspecieAndStatusConservacao(
                String nomeComum, Especie especie, StatusConservacao status);

        List<Seres> findByNomeComumContainingIgnoreCaseAndEspecie(String nomeComum, Especie especie);

        List<Seres> findByNomeComumContainingIgnoreCaseAndStatusConservacao(
                String nomeComum, StatusConservacao status);

        List<Seres> findByEspecieAndStatusConservacao(Especie especie, StatusConservacao status);

        // Caso queira listar tudo com ordenação:
        List<Seres> findAll(Sort sort);
}
