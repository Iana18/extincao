package plataforma.exticao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.dtos.EspecieRequestDTO;
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
    public ResponseEntity<List<EspecieResponseDTO>> registrar(@RequestBody List<EspecieRequestDTO> dtos) {
        List<EspecieResponseDTO> novasEspecies = dtos.stream()
                .map(dto -> especieService.registrar(dto.getNome(), dto.getDescricao(), dto.getCategoriaId()))
                .toList();
        return ResponseEntity.ok(novasEspecies);
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
            @RequestBody EspecieRequestDTO dto
    ) {
        EspecieResponseDTO atualizada = especieService.atualizar(id, dto.getNome(), dto.getDescricao(), dto.getCategoriaId());
        return ResponseEntity.ok(atualizada);
    }

    // ------------------------ DELETE ------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        especieService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
