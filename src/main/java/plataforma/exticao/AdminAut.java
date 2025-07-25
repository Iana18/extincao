/*package plataforma.exticao;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import plataforma.exticao.model.Usuario;
import plataforma.exticao.repository.UsuarioRepository;

import java.util.Optional;

@Component
public class AdminAut {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void initAdminUser() {
        String adminUid = "fJxP2Y5wPJYwgZVlWuAH0hKlIRj"; // UID do Firebase
        Optional<Usuario> adminExistente = usuarioRepository.findByUid(adminUid);

        if (adminExistente.isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUid(adminUid);
            admin.setEmail("admin@gmail.com");
            admin.setNome("Administrador");
            admin.setTipo("ADMIN");
            usuarioRepository.save(admin);
            System.out.println("✔ Usuário administrador criado com sucesso.");
        } else {
            System.out.println("ℹ️ Usuário administrador já existe.");
        }
    }
}
*/