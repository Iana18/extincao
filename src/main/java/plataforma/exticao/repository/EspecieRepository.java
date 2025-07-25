package plataforma.exticao.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plataforma.exticao.model.Especie;
import plataforma.exticao.model.StatusAprovacao;
import plataforma.exticao.model.StatusConservacao;
import plataforma.exticao.model.TipoEspecie;

import java.util.List;
@Repository
public interface EspecieRepository extends JpaRepository<Especie, Long> {


        List<Especie> findByStatusAprovacao(StatusAprovacao status);

        List<Especie> findByNomeComumContainingIgnoreCase(String nome);

        List<Especie> findAll();

        List<Especie> findByTipo(TipoEspecie tipo);

        List<Especie> findByStatusConservacao(StatusConservacao statusConservacao);


        List<Especie> findByNomeComumContainingIgnoreCaseAndTipoAndStatusConservacao(String nomeComum, TipoEspecie tipo, StatusConservacao status);

        List<Especie> findByNomeComumContainingIgnoreCaseAndTipo(String nomeComum, TipoEspecie tipo);

        List<Especie> findByNomeComumContainingIgnoreCaseAndStatusConservacao(String nomeComum, StatusConservacao status);

        List<Especie> findByTipoAndStatusConservacao(TipoEspecie tipo, StatusConservacao status);

        //List<Especie> findByNomeComumContainingIgnoreCase(String nomeComum);



}