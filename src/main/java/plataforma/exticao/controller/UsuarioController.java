package plataforma.exticao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.model.Usuario;
import plataforma.exticao.model.Quiz;
import plataforma.exticao.service.UsuarioService;
import plataforma.exticao.service.QuizService;
import plataforma.exticao.dtos.UsuarioResponseDTO;
import plataforma.exticao.dtos.JogoResultadoDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private QuizService quizService;

    // Criar usuário
    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody Usuario usuario) {
        try {
            Usuario salvo = usuarioService.salvar(usuario);
            return ResponseEntity.ok(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Listar todos os usuários
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable String id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar usuário com detalhes do progresso
    @GetMapping("/{id}/detalhes")
    public ResponseEntity<UsuarioResponseDTO> buscarDetalhes(@PathVariable String id) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> {
                    UsuarioResponseDTO dto = new UsuarioResponseDTO(
                            usuario.getId(),
                            usuario.getLogin(),
                            usuario.getEmail(),
                            usuario.getNomeCompleto(),
                            usuario.getRole(),
                            usuario.getNivelAtual(),           // int nivelAtual
                            usuario.getQuizzesCompletos()     // Set<Quiz> quizzesCompletos
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Jogar quiz e atualizar progresso
    @PostMapping("/{usuarioId}/jogar/{quizId}")
    public ResponseEntity<?> jogarQuiz(
            @PathVariable String usuarioId,
            @PathVariable Long quizId,
            @RequestBody List<Long> respostasSelecionadas) {

        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(usuarioId);
        if (usuarioOpt.isEmpty()) return ResponseEntity.notFound().build();
        Usuario usuario = usuarioOpt.get();

        Optional<JogoResultadoDTO> resultadoOpt = quizService.jogarQuiz(quizId, respostasSelecionadas);
        if (resultadoOpt.isEmpty()) return ResponseEntity.notFound().build();

        JogoResultadoDTO resultado = resultadoOpt.get();

        // Atualiza progresso se o usuário passou no quiz
        if (resultado.isPassou()) {
            quizService.buscarPorId(quizId)
                    .ifPresent(quiz -> usuarioService.atualizarProgresso(usuario, quiz));
        }

        return ResponseEntity.ok(resultado);
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
