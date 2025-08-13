package plataforma.exticao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plataforma.exticao.model.UserRole;
import plataforma.exticao.model.Usuario;

import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
   // Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByLoginContainingIgnoreCase(String login);

    Optional<Usuario> findByLogin(String login);
    boolean existsByRole(UserRole role);


}

