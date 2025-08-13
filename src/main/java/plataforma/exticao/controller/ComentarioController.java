package plataforma.exticao.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.dtos.ComentarioResponseDTO;
import plataforma.exticao.dtos.ComentarioUpdateRequestDTO;
import plataforma.exticao.model.Comentario;
import plataforma.exticao.service.ComentarioService;

import java.util.List;

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

        return comentarioService.atualizar(id, comentarioUpdateRequestDTO)
                .map(comentarioAtualizado -> {
                    ComentarioResponseDTO responseDTO = new ComentarioResponseDTO(
                            comentarioAtualizado.getId(),
                            comentarioAtualizado.getTexto(),
                            comentarioAtualizado.getDataComentario(),
                            comentarioAtualizado.getEspecie().getId(),
                            comentarioAtualizado.getAutor().getId()
                    );
                    return ResponseEntity.ok(responseDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
