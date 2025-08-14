package plataforma.exticao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plataforma.exticao.model.Especie;
import plataforma.exticao.model.Tipo;

@Repository
interface EspecieRepository extends JpaRepository<Especie,Long> {
}
