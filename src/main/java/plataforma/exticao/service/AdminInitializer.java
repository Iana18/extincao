package plataforma.exticao.service;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import plataforma.exticao.model.UserRole;
import plataforma.exticao.model.Usuario;
import plataforma.exticao.repository.UsuarioRepository;

@Component
public class AdminInitializer {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        String loginAdmin = "admin";
        String senhaAdmin = "admin123";

        if (usuarioRepository.findByLogin(loginAdmin).isEmpty()) {
            Usuario admin = new Usuario(
                    loginAdmin,
                    passwordEncoder.encode(senhaAdmin),
                    UserRole.ADMIN
            );
            usuarioRepository.save(admin);
            System.out.println("✔ Admin criado com sucesso");
        } else {
            System.out.println("⚠ Admin já existe");
        }
    }
}