package plataforma.exticao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.dtos.ComentarioResponseDTO;
import plataforma.exticao.dtos.ComentarioUpdateRequestDTO;
import plataforma.exticao.model.Comentario;
import plataforma.exticao.repository.ComentarioRepository;
import plataforma.exticao.repository.UsuarioRepository;
import plataforma.exticao.service.ComentarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;
    private  final ComentarioRepository comentarioRepository;

    public ComentarioController(ComentarioService comentarioService, ComentarioRepository comentarioRepository) {
        this.comentarioService = comentarioService;
        this.comentarioRepository = comentarioRepository;
    }

    // Listar comentários de uma espécie
    @GetMapping("/especie/{especieId}")
    public ResponseEntity<List<ComentarioResponseDTO>> listarComentarios(@PathVariable Long especieId) {
        List<Comentario> comentarios = comentarioService.listarPorEspecie(especieId);

        List<ComentarioResponseDTO> comentariosDTO = comentarios.stream()
                .map(comentarioService::toDTO) // converte para DTO (só login do autor)
                .toList();

        return ResponseEntity.ok(comentariosDTO);
    }

    // Criar comentário
    @PostMapping("/criar")
    public ResponseEntity<ComentarioResponseDTO> criarComentario(
            @RequestBody Comentario comentario,
            @RequestParam String usuarioLogin
    ) {
        Comentario salvo = comentarioService.salvar(comentario, usuarioLogin);
        return ResponseEntity.ok(comentarioService.toDTO(salvo));
    }

    // Atualizar comentário (só autor pode)
// Atualizar comentário (só autor pode)
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarComentario(
            @PathVariable Long id,
            @RequestBody ComentarioUpdateRequestDTO updateDTO,
            @RequestParam String autorLogin
    ) {
        Optional<Comentario> atualizado = comentarioService.atualizar(id, updateDTO, autorLogin);

        if (atualizado.isPresent()) {
            // Retorna o DTO do comentário atualizado
            ComentarioResponseDTO dto = comentarioService.toDTO(atualizado.get());
            return ResponseEntity.ok(dto);
        } else {
            // Retorna erro caso o usuário não seja o autor
            return ResponseEntity.status(403)
                    .body("Você só pode atualizar seus próprios comentários");
        }
    }



}
