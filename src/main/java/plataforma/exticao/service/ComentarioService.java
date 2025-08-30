package plataforma.exticao.service;

import org.springframework.stereotype.Service;
import plataforma.exticao.dtos.ComentarioResponseDTO;
import plataforma.exticao.dtos.ComentarioUpdateRequestDTO;
import plataforma.exticao.model.Comentario;
import plataforma.exticao.model.Seres;
import plataforma.exticao.model.Usuario;
import plataforma.exticao.repository.ComentarioRepository;
import plataforma.exticao.repository.SeresRepository;
import plataforma.exticao.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final SeresRepository seresRepository;
    private final UsuarioRepository usuarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository,
                             SeresRepository seresRepository,
                             UsuarioRepository usuarioRepository) {
        this.comentarioRepository = comentarioRepository;
        this.seresRepository = seresRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Salvar comentário
    public Comentario salvar(Comentario comentario, String autorLogin) {
        Usuario autor = usuarioRepository.findByLogin(autorLogin)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Seres especie = seresRepository.findById(comentario.getEspecie().getId())
                .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));

        comentario.setAutor(autor);
        comentario.setEspecie(especie);
        comentario.setDataComentario(LocalDateTime.now());

        return comentarioRepository.save(comentario);
    }

    // Listar comentários por espécie
    public List<Comentario> listarPorEspecie(Long especieId) {
        return comentarioRepository.findByEspecieIdOrderByDataComentarioAsc(especieId);
    }

    // Atualizar comentário (só pelo autor)
    public Optional<Comentario> atualizar(Long id, ComentarioUpdateRequestDTO updateDTO, String autorLogin) {
        return comentarioRepository.findById(id)
                .filter(c -> c.getAutor().getLogin().equals(autorLogin))
                .map(c -> {
                    c.setTexto(updateDTO.texto());
                    return comentarioRepository.save(c);
                });
    }

    // Converter para DTO (frontend)
    public ComentarioResponseDTO toDTO(Comentario comentario) {
        String loginAutor = comentario.getAutor() != null ? comentario.getAutor().getLogin() : "(Desconhecido)";
        Long especieId = comentario.getEspecie() != null ? comentario.getEspecie().getId() : null;

        return new ComentarioResponseDTO(
                comentario.getId(),
                comentario.getTexto(),
                comentario.getDataComentario(),
                especieId,   // <-- usar a variável segura
                loginAutor   // <-- usar a variável segura
        );
    }

    public List<ComentarioResponseDTO> listarComentariosDTO(Long especieId) {
        return comentarioRepository.findByEspecieIdOrderByDataComentarioAsc(especieId).stream()
                .filter(c -> c.getAutor() != null) // só inclui comentários com autor
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
