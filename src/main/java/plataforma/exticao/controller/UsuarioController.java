package plataforma.exticao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.model.Usuario;
import plataforma.exticao.service.UsuarioService;

import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
//@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody Usuario usuario){
        try {
            Usuario salvo = usuarioService.salvar(usuario);
            return ResponseEntity.ok(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public List<Usuario>listar(){
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario>buscarPorId(@PathVariable String id){
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletar(@PathVariable String id){
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
