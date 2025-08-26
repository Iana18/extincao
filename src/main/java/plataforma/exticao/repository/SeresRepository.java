package plataforma.exticao.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import plataforma.exticao.model.*;

import java.util.List;
@Repository
public interface SeresRepository extends JpaRepository<Seres, Long>, JpaSpecificationExecutor<Seres> {

        // Filtragem por Status de Aprovação
       List<Seres> findByStatusAprovacao(StatusAprovacao status);

        // Filtragem por Nome Comum
      //  List<Seres> findByNomeComumContainingIgnoreCase(String nome);

        // Filtragem por Tipo
      //  List<Seres> findByTipo(Tipo tipo);

        // Filtragem por Status de Conservação
       // List<Seres> findByStatusConservacao(StatusConservacao statusConservacao);

        // Filtragem por Espécie (lista)
       // List<Seres> findByEspecieIn(List<Especie> especies);

        // Combinações com Nome Comum, Espécie e Status
       // List<Seres> findByNomeComumContainingIgnoreCaseAndEspecieInAndStatusConservacao(
             //   String nomeComum, List<Especie> especies, StatusConservacao status);

      //  List<Seres> findByNomeComumContainingIgnoreCaseAndEspecieIn(
         //       String nomeComum, List<Especie> especies);

        //List<Seres> findByEspecieInAndStatusConservacao(
               // List<Especie> especies, StatusConservacao status);

        // Combinações com Nome Comum, Tipo e Status
        //List<Seres> findByNomeComumContainingIgnoreCaseAndTipoAndStatusConservacao(
                //String nomeComum, Tipo tipo, StatusConservacao status);

       // List<Seres> findByNomeComumContainingIgnoreCaseAndTipo(
             //   String nomeComum, Tipo tipo);

       // List<Seres> findByTipoAndStatusConservacao(
               // Tipo tipo, StatusConservacao status);

        // Combinações com Nome Comum e Status
        //List<Seres> findByNomeComumContainingIgnoreCaseAndStatusConservacao(
              //  String nomeComum, StatusConservacao status);

        // Combinações com Espécie e Status
       // List<Seres> findByEspecieAndStatusConservacao(
               // Especie especie, StatusConservacao status);

      //  List<Seres> findByNomeComumContainingIgnoreCaseAndEspecieAndStatusConservacao(
//                String nomeComum, Especie especie, StatusConservacao status);

       // List<Seres> findByNomeComumContainingIgnoreCaseAndEspecie(
           //     String nomeComum, Especie especie);

       // List<Seres> findByEspecie(Especie especie);

        // Listar todos com ordenação
       // List<Seres> findAll(Sort sort);
}
