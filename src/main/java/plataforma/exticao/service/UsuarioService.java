package plataforma.exticao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.exticao.model.UserRole;
import plataforma.exticao.repository.UsuarioRepository;
import plataforma.exticao.model.Usuario;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {
        // Se o usuário for ADMIN, verifica se já existe outro admin cadastrado
        if (usuario.getRole() == UserRole.ADMIN) {
            boolean existeAdmin = usuarioRepository.existsByRole(UserRole.ADMIN);
            if (existeAdmin) {
                throw new IllegalArgumentException("Já existe um usuário administrador cadastrado.");
            }
        }
        // Aqui você pode colocar lógica para criptografar senha antes de salvar, se quiser

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(String id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorNome(String login){
        return usuarioRepository.findByLoginContainingIgnoreCase(login);
    }

    public void deletar(String id){
        usuarioRepository.deleteById(id);
    }
}
