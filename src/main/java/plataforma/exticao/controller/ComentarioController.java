package plataforma.exticao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
}