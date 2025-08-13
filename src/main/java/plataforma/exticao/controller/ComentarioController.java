package plataforma.exticao.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.dtos.ComentarioResponseDTO;
import plataforma.exticao.dtos.ComentarioUpdateRequestDTO;
import plataforma.exticao.model.Comentario;
import plataforma.exticao.model.Usuario;
import plataforma.exticao.service.ComentarioService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comentarios")
//@CrossOrigin(origins = "*")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping("/criar")
    public ResponseEntity<Comentario> salvar(@RequestBody Comentario comentario) {
        return ResponseEntity.ok(comentarioService.salvar(comentario));
    }

    @GetMapping("/especie/{especieId}")
    public List<Comentario> listarPorEspecie(@PathVariable Long especieId) {
        return comentarioService.listarPorEspecie(especieId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comentario> buscarPorId(@PathVariable Long id) {
        return comentarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        comentarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ComentarioUpdateRequestDTO comentarioUpdateRequestDTO) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usuarioIdAutenticado;

        if (principal instanceof Usuario usuario) {
            usuarioIdAutenticado = usuario.getId();
        } else {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }

        Optional<Comentario> comentarioAtualizado = comentarioService.atualizar(id, comentarioUpdateRequestDTO, usuarioIdAutenticado);

        if (comentarioAtualizado.isPresent()) {
            Comentario comentario = comentarioAtualizado.get();

            ComentarioResponseDTO responseDTO = new ComentarioResponseDTO(
                    comentario.getId(),
                    comentario.getTexto(),
                    comentario.getDataComentario(),
                    comentario.getEspecie().getId(),
                    comentario.getAutor().getId()
            );

            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(403).body("Não autorizado para atualizar esse comentário");
        }
    }


}
