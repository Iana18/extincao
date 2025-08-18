package plataforma.exticao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.dtos.CategoriaRequestDTO;
import plataforma.exticao.dtos.CategoriaResponseDTO;
import plataforma.exticao.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Listar todas as categorias
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar() {
        List<CategoriaResponseDTO> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    // Buscar categoria por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> getById(@PathVariable Long id) {
        CategoriaResponseDTO dto = categoriaService.findById(id);
        return ResponseEntity.ok(dto);
    }

    // Criar nova categoria
    @PostMapping("/criar")
    public ResponseEntity<CategoriaResponseDTO> criar(@RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO novaCategoria = categoriaService.salvar(dto);
        return ResponseEntity.ok(novaCategoria);
    }

    // Atualizar categoria
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id,
                                                          @RequestBody CategoriaRequestDTO dto) {
        try {
            CategoriaResponseDTO atualizada = categoriaService.atualizar(id, dto);
            return ResponseEntity.ok(atualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            categoriaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
