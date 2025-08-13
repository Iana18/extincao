package plataforma.exticao.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import plataforma.exticao.model.Denuncia;
import plataforma.exticao.model.StatusAprovacao;

import java.util.List;

public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {
    @Override
    List<Denuncia> findAll();
    List<Denuncia> findByStatusAprovacao(StatusAprovacao statusAprovacao);


}
