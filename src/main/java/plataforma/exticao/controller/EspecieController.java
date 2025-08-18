package plataforma.exticao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.dtos.EspecieResponseDTO;
import plataforma.exticao.service.EspecieService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/especies")
public class EspecieController {

    private final EspecieService especieService;

    public EspecieController(EspecieService especieService) {
        this.especieService = especieService;
    }

    // ------------------------ CREATE ------------------------
    @PostMapping("/registrar")
    public ResponseEntity<EspecieResponseDTO> registrar(
            @RequestParam String nome,
            @RequestParam String descricao,
            @RequestParam Long categoriaId
    ) {
        EspecieResponseDTO novaEspecie = especieService.registrar(nome, descricao, categoriaId);
        return ResponseEntity.ok(novaEspecie);
    }

    // ------------------------ READ ------------------------
    @GetMapping
    public ResponseEntity<List<EspecieResponseDTO>> listarTodas() {
        return ResponseEntity.ok(especieService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecieResponseDTO> buscarPorId(@PathVariable Long id) {
        Optional<EspecieResponseDTO> especie = especieService.buscarPorId(id);
        return especie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ------------------------ UPDATE ------------------------
    @PutMapping("/{id}")
    public ResponseEntity<EspecieResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestParam String nome,
            @RequestParam String descricao,
            @RequestParam Long categoriaId
    ) {
        EspecieResponseDTO atualizada = especieService.atualizar(id, nome, descricao, categoriaId);
        return ResponseEntity.ok(atualizada);
    }

    // ------------------------ DELETE ------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        especieService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
